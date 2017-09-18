package com.br.gbhaters.tracking

import java.io.File

class CodeGenerator(private val prefix: String = "FAF", private val suffix: String = "FR") {
    private fun makeCode(token: String): String {
        return this.prefix + token + this.suffix
    }

    private fun zeroPad(value: Int, length: Int): String = value.toString().padStart(length, '0')

    fun generateAllCodes(): ArrayList<String> {
        val codeList = ArrayList<String>()

        for (cityCode in 0 until 100) {
            for (packageNumber in 0 until 1000) {
                val paddedPackageNumber = zeroPad(packageNumber, 3)
                val paddedCityCode = zeroPad(cityCode, 2)
                codeList.add(makeCode(paddedCityCode + paddedPackageNumber))
            }
        }

        return codeList
    }
}


fun main(args: Array<String>) {
    val codeGenerator = CodeGenerator("EY8930", suffix = "FR")

    val codeList = codeGenerator.generateAllCodes()

    val chunkSize = 1000

    var chunkCount = 0

    File("todos_codigos.txt").bufferedWriter().use { out ->
        codeList.forEach {
            out.write("$it \n")
        }
    }

}