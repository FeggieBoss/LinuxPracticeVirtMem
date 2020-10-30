package ru.emkn.VirtualMemory

import java.io.BufferedWriter

//OPT замещается страница, к которой дольше всего не будет обращений
fun OPT(
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

        // nextApply - следующее обращение к странице
        // frameToDelId - кадр который замещаем, pageToDel - страница внутри замещаемого кадра
        var pageToDel = Page(-1, -1)
        var frameToDelId = 0
        var nextApply = 0

        for (frameId in 0 until framesNumb) {
            if (memoryFrames[frameId].pageId == requests[reqId]) {
                pageToDel = memoryFrames[frameId]
                frameToDelId = frameId
                break
            }


            var next = requestsNumb
            for (i in reqId + 1 until requestsNumb) {
                if (requests[i] == memoryFrames[frameId].pageId) {
                    next = i
                    break
                }
            }

            if (nextApply < next) {
                nextApply = next
                pageToDel = memoryFrames[frameId]
                frameToDelId = frameId
            }
        }

        val answer = RequestAns(isIn(frameToDelId, pageToDel, requests[reqId]), reqId + 1, requests[reqId])
        answers.add(answer)

        memoryFrames[frameToDelId] = Page(requests[reqId], reqId + 1)
    }

    ("Result of OPT (optimal) work:").print(writer)
    printAnswers(writer, answerToPrefix)
    printAnswers(writer, answers)
    ("The sum of answers of the second type for OPT: ${countSecType(answers)}").print(writer)
}