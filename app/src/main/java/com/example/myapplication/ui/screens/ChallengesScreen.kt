package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.data.ChallengeEntity
import com.example.myapplication.ui.components.PressableIconButton
import com.example.myapplication.ui.components.TitleTopBar

@Composable
fun ChallengesScreen(
    challenges: List<ChallengeEntity>,
    onBack: () -> Unit,
    onAddChallenge: (String) -> Unit,
    onUpdateChallenge: (Long, String) -> Unit,
    onDeleteChallenge: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<ChallengeEntity?>(null) }
    var deleteTarget by remember { mutableStateOf<ChallengeEntity?>(null) }

    if (showAddDialog) {
        AddChallengeDialog(
            onDismiss = { showAddDialog = false },
            onSave = { description ->
                onAddChallenge(description)
                showAddDialog = false
            }
        )
    }

    editTarget?.let { target ->
        EditChallengeDialog(
            initialText = target.description,
            onDismiss = { editTarget = null },
            onSave = { description ->
                onUpdateChallenge(target.id, description)
                editTarget = null
            }
        )
    }

    deleteTarget?.let { target ->
        DeleteChallengeDialog(
            description = target.description,
            onDismiss = { deleteTarget = null },
            onConfirm = {
                onDeleteChallenge(target.id)
                deleteTarget = null
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B2B2B))
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TitleTopBar(title = "Retos", onBack = onBack)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(challenges, key = { it.id }) { challenge ->
                    ChallengeItem(
                        challenge = challenge,
                        onEdit = { editTarget = challenge },
                        onDelete = { deleteTarget = challenge }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = Color(0xFFFF8A00),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Text(text = "+", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ChallengeItem(
    challenge: ChallengeEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F1F1F), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.LocalFireDepartment,
                contentDescription = "Reto",
                tint = Color(0xFFFF8A00),
                modifier = Modifier.size(22.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PressableIconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFFFF8A00),
                        modifier = Modifier.size(20.dp)
                    )
                }
                PressableIconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFFF8A00),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color(0xFF3C3C3C))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = challenge.description, color = Color.White)
    }
}

@Composable
private fun AddChallengeDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val enabled = text.isNotBlank()

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(text = "Agregar reto", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(text = "Escriba el reto") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFF8A00),
                    unfocusedIndicatorColor = Color(0xFFFF8A00)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Cancelar", color = Color(0xFFFF8A00))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSave(text) },
                    enabled = enabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (enabled) Color(0xFFFF8A00) else Color(0xFFBDBDBD)
                    )
                ) {
                    Text(text = "Guardar", color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun EditChallengeDialog(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(text = "Editar reto", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFF8A00),
                    unfocusedIndicatorColor = Color(0xFFFF8A00)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Cancelar", color = Color(0xFFFF8A00))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSave(text) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A00))
                ) {
                    Text(text = "Guardar", color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun DeleteChallengeDialog(
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Desea eliminar el siguiente reto?:",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "NO", color = Color(0xFFFF8A00))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "SI", color = Color(0xFFFF8A00))
                }
            }
        }
    }
}

