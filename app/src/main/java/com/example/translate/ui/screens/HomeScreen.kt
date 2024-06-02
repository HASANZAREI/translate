package com.example.translate.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translate.R
import com.example.translate.models.BaseModel
import com.example.translate.ui.theme.SectionColor
import com.example.translate.util.Lang
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val translation by viewModel.translation.collectAsState()
    val languages = listOf(
        Lang(icon = R.drawable.iran, name = "Persian", symbol = "fa"),
        Lang(icon = R.drawable.turkey, name = "Turkish", symbol = "tr"),
        Lang(icon = R.drawable.china, name = "Chinese", symbol = "chi"),
        Lang(icon = R.drawable.united_states, name = "English", symbol = "en")
    )
    val (sourceLang, setSourceLang) = remember {
        mutableStateOf(languages.first())
    }
    val (targetLang, setTargetLang) = remember {
        mutableStateOf(languages.last())
    }
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val copyToClipboard: (String) -> Unit = {
        val clipData = ClipData.newPlainText(
            "Translated Text",
            it
        )
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
    }
    var sourceChange by remember {
        mutableStateOf(false)
    }
    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            LazyVerticalGrid(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(languages) { languages ->
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SectionColor)
                            .aspectRatio(1f)
                            .clickable {
                                if (sourceChange) {
                                    setSourceLang(languages)
                                } else {
                                    setTargetLang(languages)
                                }
                                dialogOpen = false
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(60.dp),
                            painter = painterResource(id = languages.icon),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(18.dp))
                        Text(text = languages.name)
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Translate")
                }
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = paddings.calculateTopPadding(), bottom = 8.dp)
        ) {
            item { Spacer(modifier = Modifier.size(16.dp)) }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(SectionColor)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                sourceChange = true
                                dialogOpen = true
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = sourceLang.icon),
                            contentDescription = sourceLang.name
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = sourceLang.name)
                    }
                    Icon(
                        modifier = Modifier
                            .clickable {
                                val s = sourceLang
                                val t = targetLang
                                setSourceLang(t)
                                setTargetLang(s)
                            },
                        painter = painterResource(id = R.drawable.ic_swap),
                        contentDescription = ""
                    )
                    Row(
                        modifier = Modifier
                            .clickable {
                                sourceChange = false
                                dialogOpen = true
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = targetLang.name)
                        Spacer(modifier = Modifier.size(8.dp))
                        Image(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = targetLang.icon),
                            contentDescription = targetLang.name
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.size(16.dp)) }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(SectionColor)
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val (query, setQuery) = remember {
                        mutableStateOf("")
                    }
                    LaunchedEffect(query) {
                        delay(500)
                        if (query.isNotEmpty()) {
                            viewModel.translate(
                                query = query,
                                source = sourceLang.symbol,
                                target = targetLang.symbol
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = sourceLang.name, color = MaterialTheme.colorScheme.primary)
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    setQuery("")
                                    viewModel.clear()
                                },
                            imageVector = Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        value = query,
                        onValueChange = {
                            setQuery(it)
                        }
                    ) {
                        if (query.isNotEmpty()) {
                            val int = remember {
                                MutableInteractionSource()
                            }
                            TextFieldDefaults.TextFieldDecorationBox(
                                value = query,
                                innerTextField = it,
                                enabled = true,
                                singleLine = false,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = int,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            )
                        } else {
                            Text(text = "Your Text...")
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.size(8.dp)) }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(SectionColor)
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = targetLang.name, color = MaterialTheme.colorScheme.primary)
                    when (val response = translation) {
                        is BaseModel.Loading -> {
                            Text(text = "Loading...")
                        }

                        is BaseModel.Success -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = response.data.responseData.translatedText)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .clickable {
                                                copyToClipboard(
                                                    response.data.responseData.translatedText
                                                )
                                            },
                                        painter = painterResource(id = R.drawable.ic_copy),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }

                        is BaseModel.Error -> {
                            Text(text = response.message)
                        }

                        null -> {
                            Text(text = "Translated Text...")
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.size(8.dp)) }
            item {
                AnimatedVisibility(
                    visible = translation is BaseModel.Success,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    runCatching {
                        translation as BaseModel.Success
                    }.onSuccess { data ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            repeat(data.data.matches.count()) {
                                val match = data.data.matches[it]
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .clickable { copyToClipboard(match.translation) }
                                        .padding(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .weight(0.7f),
                                            text = match.translation
                                        )
                                        Text(
                                            modifier = Modifier
                                                .weight(0.3f),
                                            text = "${match.match * 100}%",
                                            textAlign = TextAlign.End
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(
                                        text = match.source + " to " + match.target,
                                        color = Color.Gray,
                                        fontSize = 10.sp
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "By: ${match.createdBy}", color = Color.White, fontSize = 12.sp)
                                        Text(text = "Usage Count: ${match.usageCount}", color = Color.Gray, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}