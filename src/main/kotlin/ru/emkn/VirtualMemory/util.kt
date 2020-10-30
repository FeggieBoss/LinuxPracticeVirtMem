package ru.emkn.VirtualMemory

import java.io.BufferedWriter
import java.io.File

// Ответ на очередной запрос: сам ответ, номера запроса и странички к которой обращались
class RequestAns(val ans: String, val requestId: Int, val pageId: Int)

// Номер страницы и номер запроса в котором мы к ней обратились(в 0-индексации)
class Page(val pageId: Int, val requestId: Int)

// определяет лежит ли в памяти страничка, которую хотим удалить
// и возвращает соответствующий ответ на запрос
fun isIn(frameId: Int, pageToDel: Page, pageToApply: Int): String {
    if (pageToDel.pageId == pageToApply)
        return "The page number ${pageToDel.pageId} is already loaded into RAM, nothing needs to be done"

    return "Need to replace frame number ${frameId + 1} (with page ${pageToDel.pageId} inside)"
}

// разбиваем входную строку на массив чисел
fun parser(data: String): MutableList<Int> {
    val raw = data.split(' ').toMutableList()
    val prepared = mutableListOf<Int>()
    for (elem in raw)
        prepared.add(elem.toInt())

    return prepared
}

fun String.print(writer: BufferedWriter?) {
    if (writer != null)
        writer.appendLine(this)
    else
        println(this)
}


// возвращает BufferedWriter для файла в который нужно выводить данные
// если нет надобности выводить в файл - вернем случайное значение, потому что мы не будем использовать writer
fun getWriter(args: Array<String>): BufferedWriter? {
    if (args.size > 1) {
        val file = File(args[1])
        return file.bufferedWriter()
    }
    return null
}

// отдает шаблон для случая, когда кладем в пустую ячейку
fun emptyPrint(frameId: Int): String {
    return "Put in an empty frame ${frameId + 1}"
}

// выводим ответы на запросы
fun printAnswers(writer: BufferedWriter?, answers: List<RequestAns>) {
    for (answer in answers)
        (answer.requestId.toString() + ". Page " + answer.pageId.toString() + ": " + answer.ans).print(writer)
}

// считаем количество замещений
fun countSecType(answers: List<RequestAns>): Int {
    var counter = 0
    for (elem in answers)
        if (elem.ans[0] == 'N')
            ++counter

    return counter
}

// заполняет пустые кадры оперативной памяти
// возвращает номер запроса с которого нужно начинать применять алгоритмы замещения
fun fill(
    frames: Int,
    memoryFrames: MutableList<Page>,
    requestsNumb: Int,
    requests: MutableList<Int>,
    answerToPrefix: MutableList<RequestAns>,
    isFifo: Boolean
): Int {
    memoryFrames.clear()
    answerToPrefix.clear()

    for (j in 0 until frames)
        memoryFrames.add(Page(-1, -1))

    for (reqId in 0 until requestsNumb) {
        var isInRAM = false
        for (frameId in 0 until frames) {
            if (memoryFrames[frameId].pageId == requests[reqId]) {
                isInRAM = true
                val answer =
                    RequestAns(isIn(frameId, memoryFrames[frameId], requests[reqId]), reqId + 1, requests[reqId])
                answerToPrefix.add(answer)

                if (!isFifo)
                    memoryFrames[frameId] = Page(requests[reqId], reqId)
                break
            }
        }

        if (!isInRAM) {
            var isVacant = false
            for (frameId in 0 until frames) {
                if (memoryFrames[frameId].pageId == -1) {
                    isVacant = true
                    val answer = RequestAns(emptyPrint(frameId), reqId + 1, requests[reqId])
                    answerToPrefix.add(answer)

                    memoryFrames[frameId] = Page(requests[reqId], reqId)
                    break
                }
            }

            if (!isVacant)
                return reqId
        }
    }

    // все запросы выполняются без алгоритмов замещения
    // вернем такое значение, при котором алгоритмы замещения не будут запускаться
    return requestsNumb
}
