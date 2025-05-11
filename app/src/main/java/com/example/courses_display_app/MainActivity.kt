package com.example.courses_display_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.courses_display_app.ui.theme.Courses_Display_AppTheme

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Courses_Display_AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            CourseScreen(onBackClicked = { shouldShowOnboarding = true })
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Addis Ababa University!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("See Courses")
        }
    }
}

@Composable
fun CourseScreen(onBackClicked: () -> Unit) {
    val courses = listOf(
        Course(
            title = "📚 Kotlin Programming",
            code = "PRG101",
            creditHours = 3,
            description = "Master Kotlin for Android and backend development.",
            prerequisites = "None"
        ),
        Course(
            title = "🐍 Python for Data Science",
            code = "PRG102",
            creditHours = 4,
            description = "Learn Python for data analysis and machine learning.",
            prerequisites = "None"
        ),
        Course(
            title = "☕ Advanced Java Programming",
            code = "PRG201",
            creditHours = 3,
            description = "Explore Java for enterprise applications and design patterns.",
            prerequisites = "PRG101"
        ),
        Course(
            title = "📱 Mobile App Development",
            code = "PRG202",
            creditHours = 4,
            description = "Build Android apps using Jetpack Compose and Kotlin.",
            prerequisites = "PRG101"
        ),
        Course(
            title = "🌐 Web Development with JavaScript",
            code = "PRG301",
            creditHours = 3,
            description = "Create dynamic websites using JavaScript and React.",
            prerequisites = "None"
        ),
        Course(
            title = "🛠 Functional Programming",
            code = "PRG302",
            creditHours = 3,
            description = "Understand functional programming with Scala and Kotlin.",
            prerequisites = "PRG101"
        ),
        Course(
            title = "📦 Software Engineering Principles",
            code = "PRG401",
            creditHours = 4,
            description = "Learn agile development and software design.",
            prerequisites = "PRG201"
        ),
        Course(
            title = "🤖 Machine Learning with Python",
            code = "PRG402",
            creditHours = 3,
            description = "Dive into ML algorithms and frameworks like TensorFlow.",
            prerequisites = "PRG102"
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClicked() }
            )

            // Spacer to push the text to the center
            Spacer(modifier = Modifier.weight(1f))

            // Centered text
            Text(
                text = "Academic Courses",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )

            // Spacer to balance the layout
            Spacer(modifier = Modifier.weight(1f))
        }

        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(courses.size) { index ->
                CourseItem(course = courses[index])
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CourseItem(course:Course) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(expanded, label = "expand")
    val rotation by transition.animateFloat(label = "rotation") { if (it) 180f else 0f }
    val scale by transition.animateFloat(label = "scale") { if (it) 1.05f else 1f }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    course.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.rotate(rotation)
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand Course")
                }
            }

            Text("Code: ${course.code}", style = MaterialTheme.typography.bodyMedium)
            Text("Credit Hours: ${course.creditHours}", style = MaterialTheme.typography.bodyMedium)

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text("Description: ${course.description}", style = MaterialTheme.typography.bodyMedium)
                    Text("Prerequisites: ${course.prerequisites}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseScreenPreview() {
    Courses_Display_AppTheme {
        CourseScreen(onBackClicked = { /* Do nothing for preview */ })
    }
}