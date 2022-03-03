package io.hikarilan.chsistatusonlineverification4j

import org.intellij.lang.annotations.Pattern
import org.jsoup.nodes.Document
import java.util.*

const val timePattern = "(\\d{4})年(\\d{1,2})月(\\d{1,2})日"

val compiledTimePattern = timePattern.toPattern()

const val updateTimePattern = "更新日期：$timePattern"

val compiledUpdateTimePattern = updateTimePattern.toPattern()

const val rawIdNumberPattern = "(^\\d{15}\$)|(^\\d{18}\$)|(^\\d{17}(\\d|X|x)\$)"

val compiledIdNumberPattern = rawIdNumberPattern.toPattern()

const val rawNationalityPattern = "[\\u4e00-\\u9fa5]{1,3}族"

val compiledNationalityPattern = rawNationalityPattern.toPattern()

data class ChsiStatus constructor(
    // 更新日期
    val updateTime: Date,
    // 姓名
    val name: String,
    // 性别
    val sex: SexEnum,
    // 证件号码
    val idNumber: String,
    // 民族
    val nationality: String,
    // 出生日期
    val birthday: Date,
    // 院校
    val college: String,
    // 层次
    val level: String,
    // 院系
    val faculty: String,
    // 班级
    val `class`: String,
    // 专业
    val profession: String,
    // 学号
    val studentId: Long,
    // 形式
    val form: String,
    // 入学时间
    val admissionTime: Date,
    // 学制
    val academicSystem: String,
    // 类型
    val type: String,
    // 学籍状态
    val status: String,
    // 在线验证码
    val onlineVerificationCode: String
) {

    constructor(
        @Pattern(updateTimePattern) rawUpdateTimeString: String,
        rawSexString: String,
        @Pattern(rawIdNumberPattern) rawIdNumberString: String,
        @Pattern(rawNationalityPattern) rawNationalityString: String,
        @Pattern(timePattern) rawBirthdayString: String,
        rawCollegeString: String,
        rawLevelString: String,
        rawFacultyString: String,
        rawClassString: String,
        rawProfessionString: String,
        rawStudentIdString: String,
        rawFormString: String,
        rawAdmissionTimeString: String,
        rawAcademicSystemString: String,
        rawTypeString: String,
        rawStatusString: String,
        rawOnlineVerificationCodeString: String
    ) : this(
        updateTime = rawUpdateTimeString.toDate(compiledUpdateTimePattern),
        name = "", // Can not get name because return value of the name is an image.
        sex = SexEnum.values().find { it.friendlyName == rawSexString }
            ?: throw IllegalArgumentException("Unknown sex string: $rawSexString"),
        idNumber = checkPatternOrGet(compiledIdNumberPattern, rawIdNumberString),
        nationality = checkPatternOrGet(compiledNationalityPattern, rawNationalityString),
        birthday = rawBirthdayString.toDate(),
        college = rawCollegeString,
        level = rawLevelString,
        faculty = rawFacultyString,
        `class` = rawClassString,
        profession = rawProfessionString,
        studentId = rawStudentIdString.toLongOrNull()
            ?: throw IllegalArgumentException("Invalid student id string: $rawStudentIdString"),
        form = rawFormString,
        admissionTime = rawAdmissionTimeString.toDate(),
        academicSystem = rawAcademicSystemString,
        type = rawTypeString,
        status = rawStatusString,
        onlineVerificationCode = rawOnlineVerificationCodeString
    )

    constructor(
        document: Document
    ) : this(
        rawUpdateTimeString = document.getElementById("zbrq-title")?.text()?.trim()
            ?: throw IllegalArgumentException("Can not get update time string."),
        rawSexString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[1]/tbody/tr[2]/td[2]/div")
            .text(),
        rawIdNumberString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[1]/tbody/tr[2]/td[4]/div")
            .text(),
        rawNationalityString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[2]/div")
            .text(),
        rawBirthdayString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[4]/div")
            .text(),
        rawCollegeString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[1]/td[2]/div")
            .text(),
        rawLevelString = document.selectXpath("//html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[1]/td[4]/div")
            .text(),
        rawFacultyString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[2]/td[2]/div")
            .text(),
        rawClassString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[2]/td[4]/div")
            .text(),
        rawProfessionString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[3]/td[2]/div")
            .text(),
        rawStudentIdString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[3]/td[4]/div")
            .text(),
        rawFormString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[4]/td[2]/div")
            .text(),
        rawAdmissionTimeString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[4]/td[4]/div")
            .text(),
        rawAcademicSystemString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[4]/td[6]/div")
            .text(),
        rawTypeString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[5]/td[2]/div")
            .text(),
        rawStatusString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[1]/table[2]/tbody/tr[5]/td[4]/div")
            .text(),
        rawOnlineVerificationCodeString = document.selectXpath("/html/body/div[4]/div[3]/div/div[4]/div[2]/div/div/div[2]/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/span[1]")
            .text()
    )


    enum class SexEnum(val friendlyName: String) {
        MALE("男"),
        FEMALE("女"),
    }

}