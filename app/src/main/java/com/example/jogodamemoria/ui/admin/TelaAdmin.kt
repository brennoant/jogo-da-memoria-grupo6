package com.example.jogodamemoria.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.data.local.AppDatabase
import com.example.jogodamemoria.data.local.Carta
import com.example.jogodamemoria.data.remote.FirebaseCartasService
import com.example.jogodamemoria.data.repository.CartasRepository

@Composable
fun TelaAdmin(
    onTrocarPerfil: () -> Unit,
    viewModel: AdminViewModel = viewModel(
        factory = AdminViewModelFactory(
            CartasRepository(
                AppDatabase.getDatabase(LocalContext.current).cartaDAO(),
                FirebaseCartasService()
            )
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConteudoAdmin(
        uiState = uiState,
        onSimboloChange = { viewModel.onSimboloChange(it) },
        onNomeChange = { viewModel.onNomeChange(it) },
        onTemaChange = { viewModel.onTemaChange(it) },
        onSave = { viewModel.onSave() },
        onEdit = { viewModel.onEdit(it) },
        onDelete = { viewModel.onDelete(it) },
        onFiltroTema = { viewModel.onFiltroTema(it) },
        onSincronizar = { viewModel.onSincronizar() },
        onTrocarPerfil = onTrocarPerfil
    )
}

// Só desenha a tela; não conhece banco nem Firebase, por isso funciona no Preview
@Composable
fun ConteudoAdmin(
    uiState: AdminUiState,
    onSimboloChange: (String) -> Unit,
    onNomeChange: (String) -> Unit,
    onTemaChange: (String) -> Unit,
    onSave: () -> Unit,
    onEdit: (Carta) -> Unit,
    onDelete: (Carta) -> Unit,
    onFiltroTema: (String?) -> Unit,
    onSincronizar: () -> Unit,
    onTrocarPerfil: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Formulário de cadastro/edição
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = uiState.simbolo,
                    onValueChange = onSimboloChange,
                    label = { Text("Símbolo (emoji)") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = uiState.nome,
                    onValueChange = onNomeChange,
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Temas.TODOS.forEach { tema ->
                        FilterChip(
                            selected = uiState.tema == tema,
                            onClick = { onTemaChange(tema) },
                            label = { Text(tema) }
                        )
                    }
                }
                Button(
                    onClick = onSave,
                    enabled = !uiState.carregando,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (uiState.cartaEmEdicao == null) "Adicionar" else "Salvar")
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onSincronizar, enabled = !uiState.carregando) {
                Text("Sincronizar")
            }
            OutlinedButton(onClick = onTrocarPerfil) {
                Text("Trocar perfil")
            }
        }

        if (uiState.carregando) {
            Text("Carregando...")
        }
        uiState.mensagemErro?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        // Filtro da lista (null = Todos)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = uiState.filtroTema == null,
                onClick = { onFiltroTema(null) },
                label = { Text("Todos") }
            )
            Temas.TODOS.forEach { tema ->
                FilterChip(
                    selected = uiState.filtroTema == tema,
                    onClick = { onFiltroTema(tema) },
                    label = { Text(tema) }
                )
            }
        }

        // Lista das cartas (só as do filtro escolhido)
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.cartasFiltradas) { carta ->
                UmaCarta(
                    carta = carta,
                    onEdit = { onEdit(carta) },
                    onDelete = { onDelete(carta) }
                )
            }
        }
    }
}

// Uma linha da lista: emoji, nome, tema e os botões de editar e excluir
@Composable
fun UmaCarta(carta: Carta, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${carta.simbolo}  ${carta.nome} (${carta.tema})",
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir")
            }
        }
    }
}

// Preview com cartas falsas (o Preview não consegue abrir o banco nem o Firebase)
@Preview(showBackground = true)
@Composable
fun TelaAdminPreview() {
    val cartasFalsas = listOf(
        Carta(id = 1, simbolo = "🐶", nome = "Cachorro", tema = Temas.ANIMAIS),
        Carta(id = 2, simbolo = "🍎", nome = "Maçã", tema = Temas.FRUTAS)
    )
    ConteudoAdmin(
        uiState = AdminUiState(cartas = cartasFalsas, cartasFiltradas = cartasFalsas),
        onSimboloChange = {},
        onNomeChange = {},
        onTemaChange = {},
        onSave = {},
        onEdit = {},
        onDelete = {},
        onFiltroTema = {},
        onSincronizar = {},
        onTrocarPerfil = {}
    )
}
