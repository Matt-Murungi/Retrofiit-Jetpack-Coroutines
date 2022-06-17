package com.example.retrofitcoroutinesandjetpack.component

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitcoroutinesandjetpack.model.QuestionItem
import com.example.retrofitcoroutinesandjetpack.screens.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel){
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Questions: ......Loading......")
    }else{
        Log.d("Loading", "Questions: .....Loading Stopped")
        questions?.forEach{ questionItem ->
            Log.d("Result", "Questions: ${questionItem.question}")
        }
    }


}



@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit
){

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerState= remember(question){
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question){
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question){
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }


    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
QuestionTracker()

            Column{

                Text(text="What is life? ")
                choicesState.forEachIndexed{
                    index, answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)

                    ){
                        RadioButton(selected = (answerState.value == index) , onClick = {
                        updateAnswer(index)
                        },
                        modifier = Modifier.padding(start = 16.dp),
                        colors = RadioButtonDefaults
                            .colors(
                                selectedColor = if (correctAnswerState.value ==true 
                                    && index == answerState.value ){
                                    Color.Green.copy(alpha = 0.2f)
                                }else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                            )
                    }
                }
            }
        }

    }
}



@Preview
@Composable
fun QuestionTracker(counter:Int =10, outOf:Int =100){
    Text(text= buildAnnotatedString {
        withStyle(style=ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style= SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 27.sp )){
                append("Question $counter/")
                withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 14.sp)){
                    append("$outOf")
                }
            }
        }
    })
}