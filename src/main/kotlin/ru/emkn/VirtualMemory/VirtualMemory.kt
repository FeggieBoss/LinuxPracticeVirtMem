package ru.emkn.VirtualMemory

import java.io.File

fun main(args: Array<String>) {
    val myfile = File(args[0])
    val reader = myfile.bufferedReader()
    val writer = getWriter(args)

    // считываем из файла количество обращений к страницам адресного пространства
    val requestsNumb = reader.readLine()!!.toInt()

    // считываем из файла:
    // 1)размер адресного пространства процесса(в страницах)
    // 2)количество кадров оперативной памяти
    val pagesNumb = reader.readLine()!!.toInt()
    val framesNumb = reader.readLine()!!.toInt()

    // сохраняем номера страниц адресного пространства к которым обращаемся
    var requests = parser(reader.readLine()!!)

    // заполним все кадры оперативной памяти страницами, чтобы потом уже применять алгоритмы замещения
    var memoryFrames = mutableListOf<Page>()
    var answerToPrefix = mutableListOf<RequestAns>()
    var start = fill(framesNumb, memoryFrames, requestsNumb, requests, answerToPrefix, true)
    FIFO(requestsNumb, requests, framesNumb, memoryFrames, start, answerToPrefix, writer)

    start = fill(framesNumb, memoryFrames, requestsNumb, requests, answerToPrefix, false)
    LRU(requestsNumb, requests, framesNumb, memoryFrames, start, answerToPrefix, writer)

    start = fill(framesNumb, memoryFrames, requestsNumb, requests, answerToPrefix, false)
    OPT(requestsNumb, requests, framesNumb, memoryFrames, start, answerToPrefix, writer)

    reader.close()
    writer?.close()
}
