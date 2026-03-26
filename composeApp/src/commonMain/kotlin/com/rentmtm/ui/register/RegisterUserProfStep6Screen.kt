package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_step_06

import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmCheckbox
import com.rentmtm.ui.components.MtmRadioButton
import com.rentmtm.viewmodel.RegisterViewModel

@Composable
fun RegisterUserProfStep6Screen(
    viewModel: RegisterViewModel,
    profileTitle: String = "Professional Allocated",
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.ic_step_06),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Skills and Qualifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // SEÇÃO: EDUCATION
            // ==========================================
            Text("Highest Level of Education", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("High School", viewModel.qualifications.isHighSchool, { viewModel.qualifications = viewModel.qualifications.copy(isHighSchool = it) }, Modifier.weight(1f))
                MtmCheckbox("Associate Degree", viewModel.qualifications.isAssociate, { viewModel.qualifications = viewModel.qualifications.copy(isAssociate = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Bachelor's Degree", viewModel.qualifications.isBachelor, { viewModel.qualifications = viewModel.qualifications.copy(isBachelor = it) }, Modifier.weight(1f))
                MtmCheckbox("Master's Degree", viewModel.qualifications.isMaster, { viewModel.qualifications = viewModel.qualifications.copy(isMaster = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Doctorate", viewModel.qualifications.isDoctorate, { viewModel.qualifications = viewModel.qualifications.copy(isDoctorate = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Other:", viewModel.qualifications.isOtherEdu, { viewModel.qualifications = viewModel.qualifications.copy(isOtherEdu = it) }, Modifier.width(90.dp))
                if (viewModel.qualifications.isOtherEdu) {
                    MtmTextField("", viewModel.qualifications.otherEduText, { viewModel.qualifications = viewModel.qualifications.copy(otherEduText = it) }, "Specify", Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Field of Study",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = viewModel.qualifications.fieldOfStudy,
                onValueChange = { viewModel.qualifications = viewModel.qualifications.copy(fieldOfStudy = it) },
                placeholder = { Text("Computer Science, Business, etc.", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: CERTIFICATIONS
            // ==========================================
            Text("Certifications & Licenses (if applicable)", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            viewModel.qualifications.certifications.forEachIndexed { index, certification ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Certification ${index + 1}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    if (viewModel.qualifications.certifications.size > 1) {
                        TextButton(onClick = { viewModel.removeCertification(index) }) {
                            Text("Remove", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                MtmTextField(
                    label = "Name",
                    value = certification.certName,
                    onValueChange = { viewModel.updateCertification(index, certification.copy(certName = it)) },
                    placeholder = "PMP, AWS Certified Developer"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MtmTextField(
                        label = "Issuing Organization",
                        value = certification.certOrg,
                        onValueChange = { viewModel.updateCertification(index, certification.copy(certOrg = it)) },
                        placeholder = "PMI, Amazon",
                        modifier = Modifier.weight(1f)
                    )
                    MtmDateField(
                        label = "Expiration Date",
                        value = certification.certDate,
                        onDateSelected = { viewModel.updateCertification(index, certification.copy(certDate = it)) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            OutlinedButton(
                onClick = { viewModel.addCertification() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Certification")
                Spacer(Modifier.width(8.dp))
                Text("Add Another Certification")
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: TECHNICAL SKILLS
            // ==========================================
            Text("Technical Skills/Expertise", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("IT & Software", viewModel.qualifications.isIT, { viewModel.qualifications = viewModel.qualifications.copy(isIT = it) }, Modifier.weight(1f))
                MtmCheckbox("Engineering", viewModel.qualifications.isEng, { viewModel.qualifications = viewModel.qualifications.copy(isEng = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Healthcare", viewModel.qualifications.isHealth, { viewModel.qualifications = viewModel.qualifications.copy(isHealth = it) }, Modifier.weight(1f))
                MtmCheckbox("Finance", viewModel.qualifications.isFin, { viewModel.qualifications = viewModel.qualifications.copy(isFin = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Construction", viewModel.qualifications.isConst, { viewModel.qualifications = viewModel.qualifications.copy(isConst = it) }, Modifier.weight(1f))
                MtmCheckbox("Marketing", viewModel.qualifications.isMark, { viewModel.qualifications = viewModel.qualifications.copy(isMark = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Other:", viewModel.qualifications.isOtherSkill, { viewModel.qualifications = viewModel.qualifications.copy(isOtherSkill = it) }, Modifier.width(90.dp))
                if (viewModel.qualifications.isOtherSkill) {
                    MtmTextField("", viewModel.qualifications.otherSkillText, { viewModel.qualifications = viewModel.qualifications.copy(otherSkillText = it) }, "Specify", Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: LANGUAGES SPOKEN (AGORA DINÂMICA)
            // ==========================================
            Text("Languages Spoken", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            viewModel.qualifications.languages.forEachIndexed { index, entry ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Language ${index + 1}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    if (viewModel.qualifications.languages.size > 1) {
                        TextButton(onClick = {
                            viewModel.removeLanguage(index) // ⬅️ Lógica transferida para o ViewModel
                        }) {
                            Text("Remove", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                MtmTextField(
                    label = "Language Name",
                    value = entry.language,
                    onValueChange = { newValue ->
                        viewModel.updateLanguage(index, newLanguage = newValue) // ⬅️ Atualização limpa
                    },
                    placeholder = "e.g., English, Spanish"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Fluency Level", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MtmRadioButton(
                        text = "Basic",
                        selected = entry.fluency == "Basic",
                        onClick = { viewModel.updateLanguage(index, newFluency = "Basic") } // ⬅️ Atualização limpa
                    )
                    MtmRadioButton(
                        text = "Intermediate",
                        selected = entry.fluency == "Intermediate",
                        onClick = { viewModel.updateLanguage(index, newFluency = "Intermediate") }
                    )
                    MtmRadioButton(
                        text = "Fluent",
                        selected = entry.fluency == "Fluent",
                        onClick = { viewModel.updateLanguage(index, newFluency = "Fluent") }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            OutlinedButton(
                onClick = { viewModel.addLanguage() }, // ⬅️ Lógica transferida para o ViewModel
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Language")
                Spacer(Modifier.width(8.dp))
                Text("Add Another Language")
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: PROFESSIONAL REFERENCES
            // ==========================================
            Text("Professional References", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            viewModel.qualifications.references.forEachIndexed { index, reference ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Reference ${index + 1}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    if (viewModel.qualifications.references.size > 1) {
                        TextButton(onClick = { viewModel.removeReference(index) }) {
                            Text("Remove", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MtmTextField(
                        label = "Name",
                        value = reference.name,
                        onValueChange = { viewModel.updateReference(index, reference.copy(name = it)) },
                        placeholder = "John Doe",
                        modifier = Modifier.weight(1f)
                    )
                    MtmTextField(
                        label = "Company",
                        value = reference.company,
                        onValueChange = { viewModel.updateReference(index, reference.copy(company = it)) },
                        placeholder = "ABC Corp",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MtmTextField(
                        label = "Relationship",
                        value = reference.relationship,
                        onValueChange = { viewModel.updateReference(index, reference.copy(relationship = it)) },
                        placeholder = "Former Manager",
                        modifier = Modifier.weight(1f)
                    )
                    MtmTextField(
                        label = "Phone/Email",
                        value = reference.contact,
                        onValueChange = { viewModel.updateReference(index, reference.copy(contact = it)) },
                        placeholder = "Contact info",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            OutlinedButton(
                onClick = { viewModel.addReference() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Reference")
                Spacer(Modifier.width(8.dp))
                Text("Add Another Reference")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 3. BARRA DE BOTÕES INFERIOR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Back")
                }

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Text("Next")
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep6ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep6Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}