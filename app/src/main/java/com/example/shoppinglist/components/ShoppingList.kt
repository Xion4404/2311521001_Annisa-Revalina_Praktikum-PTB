package com.example.shoppinglist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ShoppingListScreen() {
    var newItemText by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val shoppingItems = remember { mutableStateListOf<String>() }

    val filteredItems by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                shoppingItems.toList()
            } else {
                shoppingItems.filter { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping List",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔸 Input + Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newItemText,
                onValueChange = { newItemText = it },
                label = { Text("Tambah Item") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50) // Membulatkan ujung
            )
            Button(
                onClick = {
                    if (newItemText.isNotBlank()) {
                        shoppingItems.add(newItemText.trim())
                        newItemText = ""
                    }
                },
                shape = RoundedCornerShape(50) // Tombol juga membulat
            ) {
                Text("Tambah")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Search Bar membulat
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Cari Item") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada item",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredItems) { item ->
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
