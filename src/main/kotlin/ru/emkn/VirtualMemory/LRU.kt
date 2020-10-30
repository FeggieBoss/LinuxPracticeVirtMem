package ru.emkn.VirtualMemory

import java.io.BufferedWriter

// LRU замещается страница, к которой дольше всего не было обращений
fun LRU(
    requestsNumb: Int,
    requests: List<Int>,
    framesNumb: Int,
    memoryFrames: MutableList<Page>,
    start: Int,
    answerToPrefix: MutableList<RequestAns>,
    writer: BufferedWriter?
) {
    var answers = mutableListOf<RequestAns>()
    for (reqId in start until requestsNumb) {
        // frameToDelId - кадр который замещаем, pageToDel - страница внутри замещаемого кадра
        var pageToDel = Page(-1, requestsNumb)
        var frameToDelId = 0

        // перебираем все кадры и ищем такой, у страницы которого номер последнего запроса к ней минимален
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
        // иначе же просто обновим номер запроса, в котором мы обращаемся к этой странице
        // в нашем случае получается одно и то же действие
        memoryFrames[frameToDelId] = Page(requests[reqId], reqId + 1)
    }

    ("Result of LRU (least recently used) work:").print(writer)
    printAnswers(writer, answerToPrefix)
    printAnswers(writer, answers)
    ("The sum of answers of the second type for LRU: ${countSecType(answers)}").print(writer)
}