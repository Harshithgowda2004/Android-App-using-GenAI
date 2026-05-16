package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KabaddiArenaApp()
        }
    }
}

@Composable
fun KabaddiArenaApp() {

    var currentScreen by remember {
        mutableStateOf("setup")
    }

    var teamName by remember {
        mutableStateOf("")
    }

    var opponentName by remember {
        mutableStateOf("")
    }

    var finalPoints by remember {
        mutableStateOf(0)
    }

    if (currentScreen == "setup") {

        MatchSetupScreen(
            teamName = teamName,
            opponentName = opponentName,

            onTeamNameChange = {
                teamName = it
            },

            onOpponentNameChange = {
                opponentName = it
            },

            onStartMatch = {
                currentScreen = "match"
            }
        )
    }

    else if (currentScreen == "match") {

        MatchLoggerScreen(
            teamName = teamName,
            opponentName = opponentName,

            onEndMatch = { score ->
                finalPoints = score
                currentScreen = "summary"
            }
        )
    }

    else {

        MatchSummaryScreen(
            teamName = teamName,
            opponentName = opponentName,
            finalPoints = finalPoints
        )
    }
}

@Composable
fun MatchSetupScreen(
    teamName: String,
    opponentName: String,
    onTeamNameChange: (String) -> Unit,
    onOpponentNameChange: (String) -> Unit,
    onStartMatch: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Kabaddi Arena",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = teamName,
            onValueChange = onTeamNameChange,
            label = {
                Text("Your Team Name")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = opponentName,
            onValueChange = onOpponentNameChange,
            label = {
                Text("Opponent Team")
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onStartMatch
        ) {
            Text("Start Match")
        }
    }
}

@Composable
fun MatchLoggerScreen(
    teamName: String,
    opponentName: String,
    onEndMatch: (Int) -> Unit
) {

    var points by remember { mutableStateOf(0) }

    var totalRaids by remember { mutableStateOf(0) }

    var successfulRaids by remember { mutableStateOf(0) }

    var superRaids by remember { mutableStateOf(0) }

    var successfulTackles by remember { mutableStateOf(0) }

    var seconds by remember { mutableStateOf(0) }

    val actionHistory = remember {
        mutableStateListOf<String>()
    }

    val raidPoints = remember {
        mutableStateListOf<Offset>()
    }

    LaunchedEffect(Unit) {

        while (true) {

            delay(1000)

            seconds++
        }
    }

    val minutes = seconds / 60

    val remainingSeconds = seconds % 60

    val raidSuccessPercentage =
        if (totalRaids > 0)
            (successfulRaids * 100) / totalRaids
        else
            0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "$teamName vs $opponentName",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Match Time: ${minutes}:${remainingSeconds.toString().padStart(2, '0')}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Total Points: $points",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Performance Analytics",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Total Raids: $totalRaids")

                Text("Successful Raids: $successfulRaids")

                Text("Raid Success: $raidSuccessPercentage%")

                Text("Super Raids: $superRaids")

                Text("Successful Tackles: $successfulTackles")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {

                    points += 1
                    totalRaids += 1
                    successfulRaids += 1

                    actionHistory.add("Touch Point")

                    raidPoints.add(
                        Offset(
                            (100..900).random().toFloat(),
                            (100..500).random().toFloat()
                        )
                    )
                }
            ) {
                Text("Touch Point")
            }

            Button(
                onClick = {

                    points += 2
                    totalRaids += 1
                    successfulRaids += 1
                    superRaids += 1

                    actionHistory.add("Super Raid")

                    raidPoints.add(
                        Offset(
                            (100..900).random().toFloat(),
                            (100..500).random().toFloat()
                        )
                    )
                }
            ) {
                Text("Super Raid")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {

                    totalRaids += 1

                    actionHistory.add("Empty Raid")
                }
            ) {
                Text("Empty Raid")
            }

            Button(
                onClick = {

                    points += 1

                    successfulTackles += 1

                    actionHistory.add("Successful Tackle")
                }
            ) {
                Text("Tackle")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {

                    if (actionHistory.isNotEmpty()) {

                        val lastAction = actionHistory.last()

                        when (lastAction) {

                            "Touch Point" -> {

                                points -= 1
                                totalRaids -= 1
                                successfulRaids -= 1

                                if (raidPoints.isNotEmpty()) {
                                    raidPoints.removeLast()
                                }
                            }

                            "Super Raid" -> {

                                points -= 2
                                totalRaids -= 1
                                successfulRaids -= 1
                                superRaids -= 1

                                if (raidPoints.isNotEmpty()) {
                                    raidPoints.removeLast()
                                }
                            }

                            "Empty Raid" -> {
                                totalRaids -= 1
                            }

                            "Successful Tackle" -> {
                                points -= 1
                                successfulTackles -= 1
                            }
                        }

                        actionHistory.removeLast()
                    }
                }
            ) {
                Text("Undo")
            }

            Button(
                onClick = {
                    onEndMatch(points)
                }
            ) {
                Text("End Match")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Raid Heatmap",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {

            drawRect(
                color = Color(0xFFD2B48C)
            )

            drawLine(
                color = Color.White,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = 8f
            )

            raidPoints.forEach { point ->

                drawCircle(
                    color = Color.Red,
                    radius = 15f,
                    center = point
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Match Actions",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(actionHistory.reversed()) { action ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Text(
                        text = action,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MatchSummaryScreen(
    teamName: String,
    opponentName: String,
    finalPoints: Int
) {

    val aiSuggestion = when {

        finalPoints >= 15 ->
            "Excellent attacking performance! Your raids were highly effective."

        finalPoints >= 10 ->
            "Good match performance. Improve raid consistency."

        finalPoints >= 5 ->
            "Average performance. Focus on timing and escape speed."

        else ->
            "Needs improvement. Practice stamina and footwork."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Match Summary",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "$teamName vs $opponentName",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Final Points: $finalPoints",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "AI Coach Suggestion",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = aiSuggestion
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Share Performance Card")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "KABADDI ARENA",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = teamName,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Scored $finalPoints Points"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Performance Tracked Successfully"
                )
            }
        }
    }
}
