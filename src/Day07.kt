fun main() {
    fun part1(input: List<String>): Long {
        val tokens = input.map { parseLine(it) }

        val tree = buildDirectoryTree(tokens)
        return obtainDirectories(tree).filter { it.size() <= 100_000 }.sumOf { it.size() }
    }

    fun part2(input: List<String>): Long {
        val totalDiskSpace = 70_000_000
        val totalSpaceRequired = 30_000_000

        val tokens = input.map { parseLine(it) }
        val tree = buildDirectoryTree(tokens)

        // Find the space used by the root directory
        val totalSpaceUsed = obtainDirectories(tree).first { it.token.params[0] == "/" }.size()

        val totalFreeSpace = totalDiskSpace - totalSpaceUsed
        val totalSpaceToFree = totalSpaceRequired - totalFreeSpace

        return obtainDirectories(tree).filter { it.size() >= totalSpaceToFree }.minOf { it.size() }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun obtainDirectories(node: TreeNode): Set<TreeNode> {
    val directories = mutableSetOf<TreeNode>()

    if (node.token.tokenType == TokenType.DIR) {
        directories.add(node)
    }
    node.children.forEach {
        if (it.token.tokenType == TokenType.DIR) {
            directories.add(node)
            directories.addAll(obtainDirectories(it))
        }
    }

    return directories
}

fun buildDirectoryTree(tokens: List<Token>): TreeNode {
    val rootNode = TreeNode(Token(TokenType.DIR, listOf("/")), null)
    var currentNode = rootNode

    tokens.drop(1).forEach { token -> // Drop the first line as it's just CD'ing into the root dir
        when (token.tokenType) {
            TokenType.DIR -> {
                val dirNode = TreeNode(token, currentNode)

                if (!currentNode.children.contains(dirNode)) {
                    currentNode.addChild(dirNode)
                }
            }
            TokenType.CD -> {
                val dirNode = TreeNode(Token(TokenType.DIR, token.params), currentNode)

                if (dirNode.token.params[0] == "..") {
                    currentNode = currentNode.parent!!
                } else if (currentNode.children.contains(dirNode)) {
                    currentNode = currentNode.children.first { it == dirNode }
                } else {
                    currentNode.addChild(dirNode)
                }
            }
            TokenType.FILE -> {
                val fileNode = TreeNode(Token(TokenType.FILE, token.params), currentNode)

                if (!currentNode.children.contains(fileNode)) {
                    currentNode.addChild(fileNode)
                }
            }
            else -> {}
        }
    }

    return rootNode
}

fun parseLine(input: String): Token {
    return if (input.startsWith("$")) { // A command
        parseCommand(input)
    } else {
        parseOutputLine(input)
    }
}

fun parseOutputLine(input: String): Token {
    return if (input.startsWith("dir")) {
        Token(TokenType.DIR, listOf(input.substring(4))) // Remove the "dir " at the beginning to get the dir name
    } else {
        val splitInput = input.split(" ")
        val fileSize = splitInput[0]
        val fileName = splitInput[1]

        Token(TokenType.FILE, listOf(fileSize, fileName))
    }
}

fun parseCommand(input: String): Token {
    return input
        .substring(2) // Remove the $ and space at the beginning of the String
        .run {
            when (this.substring(0, 2)) {
                "cd" -> Token(TokenType.CD, listOf(this.substring(3))) // Remove the "cd ", leaving just the directory
                "ls" -> Token(TokenType.LS)
                else -> throw IllegalArgumentException("Unrecognised command: ${this.substring(0, 2)}")
            }
        }
}

data class Token(val tokenType: TokenType, val params: List<String> = listOf())

enum class TokenType {
    CD,
    LS,
    DIR,
    FILE
}

data class TreeNode(val token: Token, val parent: TreeNode?) {
    val children = mutableListOf<TreeNode>()

    fun addChild(child: TreeNode) {
        children.add(child)
    }

    fun size(): Long = size(this)

    private fun size(node: TreeNode): Long {
        return when (node.token.tokenType) {
            TokenType.FILE -> {
                node.token.params[0].toLong()
            }
            TokenType.DIR -> {
                node.children.sumOf { size(it) }
            }
            else -> {
                0L
            }
        }
    }

    override fun toString(): String {
        return "TreeNode(token=$token, parent=${parent?.token}, children=$children)"
    }
}
