package com.rentmtm.ui.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.ui.components.MtmStarRatingBar
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.viewmodel.ProfessionalReviewUiState
import com.rentmtm.viewmodel.ProfessionalReviewViewModel

@Composable
fun ProfessionalReviewScreen(
    viewModel: ProfessionalReviewViewModel,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onFinish()
    }

    ProfessionalReviewContent(
        state = state,
        onRatingChanged = viewModel::onRatingChanged,
        onCommentsChanged = viewModel::onCommentsChanged,
        onSubmit = viewModel::submitReview,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalReviewContent(
    state: ProfessionalReviewUiState,
    onRatingChanged: (Int) -> Unit,
    onCommentsChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rate Customer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Button(
                        onClick = onSubmit,
                        enabled = state.isSubmitEnabled && !state.isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text("Submit Review", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "How was the customer?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please rate your experience working with ${state.customerName}.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            MtmStarRatingBar(
                rating = state.rating,
                onRatingChanged = onRatingChanged
            )

            Spacer(modifier = Modifier.height(48.dp))

            MtmTextArea(
                label = "Additional Comments (Optional)",
                value = state.comments,
                onValueChange = onCommentsChanged,
                placeholder = "Was the customer clear with instructions?",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun ProfessionalReviewContentPreview() {
    MaterialTheme {
        ProfessionalReviewContent(
            state = ProfessionalReviewUiState(
                customerName = "Jane Doe",
                rating = 4,
                comments = "",
                isSubmitEnabled = true
            ),
            onRatingChanged = {},
            onCommentsChanged = {},
            onSubmit = {},
            onBack = {}
        )
    }
}