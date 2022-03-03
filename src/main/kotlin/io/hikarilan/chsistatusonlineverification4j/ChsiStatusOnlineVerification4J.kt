package io.hikarilan.chsistatusonlineverification4j

import org.jsoup.Jsoup

object ChsiStatusOnlineVerification4J {

    fun getStatus(onlineVerificationCode: String): ChsiStatus {

        if (onlineVerificationCode.length != 16) {
            throw IllegalArgumentException("onlineVerificationCode length must be 16")
        }

        val doc = Jsoup.connect("https://www.chsi.com.cn/xlcx/bg.do?vcode=$onlineVerificationCode").get()

        if (!doc.getElementsByClass("yzbg-tips").isEmpty()) {
            throw ChsiStatusDocumentHasBeenClosedException(
                doc.selectXpath("/html/body/div[4]/div[3]/div/div/h2").text()
            )
        }

        if (doc.getElementById("getXueLi") != null) {
            throw ChsiStatusChallengeNeededException(
                doc.selectXpath("/html/body/div[4]/div[3]/div/form/table/tbody/tr[1]/td[1]").text()
            )
        }

        return ChsiStatus(doc)
    }
}

