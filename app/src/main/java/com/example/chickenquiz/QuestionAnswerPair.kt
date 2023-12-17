package com.example.chickenquiz
//classe qui représente eles questions
data class QuestionAnswerPair(
    val question: String,
    val answerType: AnswerType,
    val answers: List<String>,
    val correctAnswerIndex: Int ,
    val level : Int
)

enum class AnswerType {  //types de questions possibles
    TEXT,
    IMAGE
}
