package ru.emkn.VirtualMemory

import org.junit.jupiter.api.*
import java.lang.IllegalArgumentException
import java.time.Duration
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.io.File

class VirtualMemoryTests {
    @Test
    fun generalTest() {
        val inputFile = File("input.txt")
        val outputFile = File("output.txt")
        val testFile = File("src/test/kotlin/ourTests.txt")
        val testFileAnswer =
            File("src/test/kotlin/ourTestsAnswer.txt")

        val readerTest = testFile.bufferedReader()
        val readerTestAnswer = testFileAnswer.bufferedReader()

        for (i in 0..999) {
            val writerInput = inputFile.bufferedWriter()
            val readerInput = inputFile.bufferedReader()
            val writerOutput = outputFile.bufferedWriter()
            val readerOutput = outputFile.bufferedReader()

            val requests = readerTest.readLine()!!.toInt()

            writerInput.appendLine(requests.toString())
            for (j in 0..2)
                writerInput.appendLine(readerTest.readLine()!!.toString())

            writerInput.close();
            readerInput.close();

            val args = listOf(
                "input.txt",
                "output.txt"
            )
            main(args.toTypedArray())

            for (j in 0..2) {
                for (k in 0 until requests + 2) {
                    val realAns = readerTestAnswer.readLine()!!.toString()
                    val outAns = readerOutput.readLine()!!.toString()

                    if (realAns!=outAns) {
                        println("Wrong answer on test${i + 1}")
                        System.exit(0)
                    }
                }
            }

            println("Correct answer on test${i + 1}")
            writerOutput.close();
            readerOutput.close();
        }
        readerTest.close()
        readerTestAnswer.close()
    }
}