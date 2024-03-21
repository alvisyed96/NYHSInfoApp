package com.syed.nyhsinfoapp.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syed.nyhsinfoapp.data.model.ApiResponseItem
import com.syed.nyhsinfoapp.data.util.Resource
import com.syed.nyhsinfoapp.presentation.theme.NYHSInfoAppTheme
import com.syed.nyhsinfoapp.presentation.viewmodel.SchoolDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val schoolDetailsViewModel: SchoolDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYHSInfoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(schoolDetailsViewModel = schoolDetailsViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(schoolDetailsViewModel: SchoolDetailsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = "main_screen") {
            MainScreen(schoolDetailsViewModel = schoolDetailsViewModel, navController)
        }
        composable(route = "detail_screen/{paragraph}") { navBackStackEntry ->
            val paragraph = navBackStackEntry.arguments?.getString("paragraph")
            if (paragraph != null) {
                DetailScreen(paragraph = paragraph)
            }
        }
    }
}

@Composable
fun MainScreen(schoolDetailsViewModel: SchoolDetailsViewModel, navController: NavController) {
    val schoolDetails by schoolDetailsViewModel.details.observeAsState(initial = Resource.Loading)
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val details = schoolDetails) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                LazyColumn {
                    items(details.response.size) { index ->
                        SchoolRowItem(item = details.response[index], navController)
                    }
                }
            }

            is Resource.Error -> {
                Text(text = details.message, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    LaunchedEffect(Unit) {
        schoolDetailsViewModel.fetchDetails()
    }
}

@Composable
fun DetailScreen(paragraph: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(5.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
                Text(text = paragraph, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun SchoolRowItem(item: ApiResponseItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(5.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    val paragraph = item.overview_paragraph
                    navController.navigate("detail_screen/$paragraph")

                },
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = item.school_name, style = MaterialTheme.typography.titleMedium)
                DbnText(text = item.dbn)
                Text(text = "VIEW DETAILS", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun DbnText(text: String) {
    Row(modifier = Modifier.padding(bottom = 5.dp)) {
        Text(text = "DBN: ", style = MaterialTheme.typography.labelMedium)
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    NYHSInfoAppTheme {}
}