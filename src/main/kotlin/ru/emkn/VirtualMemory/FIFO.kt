package ru.emkn.VirtualMemory

import java.io.BufferedWriter

// FIFO замещается та страница, которая попала в оперативную память раньше всех остальных
fun FIFO(
    requestsNumb: Int,
    requests: List<Int>,
    framesNumb: Int,
    memoryFrames: MutableList<Page>,
    start: Int,
    answerToPrefix: MutableList<RequestAns>,
    writer: BufferedWriter?
) {
    var answers = mutableListOf<RequestAns>()

    // перебираем все запросы, начиная с того, на который еще не ответили
    for (reqId in start until requestsNumb) {
        // frameToDelId - кадр который замещаем, pageToDel - страница внутри замещаемого кадра
        var pageToDel = Page(-1, requestsNumb)
        var frameToDelId = 0

        // перебираем все кадры и ищем тот, у которого номер запроса,
        // на котором в него была добавлена та или иная страница, минимален
        // или тот, в котором уже находится наша страничка(ничего замещать в таком случае не нужно)
        for (frameId in 0 until framesNumb) {
            if (memoryFrames[frameId].pageId == requests[reqId]) {
                pageToDel = memoryFrames[frameId]
                frameToDelId = frameId
                break
            }

            if (memoryFrames[frameId].requestId < pageToDel.requestId) {
                pageToDel = memoryFrames[frameId]
                frameToDelId = frameId
            }
        }

        val answer = RequestAns(isIn(frameToDelId, pageToDel, requests[reqId]), reqId + 1, requests[reqId])
        answers.add(answer)

        // Если наша страничка не лежит в кадре - замещаем
        if (answer.ans[0] != 'T')
            memoryFrames[frameToDelId] = Page(requests[reqId], reqId + 1)
    }

    ("Result of FIFO (first in - first out) work:").print(writer)
    printAnswers(writer, answerToPrefix)
    printAnswers(writer, answers)
    ("The sum of answers of the second type for FIFO: ${countSecType(answers)}").print(writer)
}