package com.example.pregnantpal.screen.DetailScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.pregnantpal.model.MaternalData
import com.example.pregnantpal.components.addButton
import com.example.pregnantpal.components.textInput
import com.example.pregnantpal.R
import com.example.pregnantpal.screen.Navigation.Screens
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PregnantPalScreen(
    navController: NavController
){

    val context = LocalContext.current


    val expanded = remember {
        mutableStateOf(false)
    }

    val pregnancyTypes = listOf("Singleton",  "Twins")
    val singleton_or_twins = remember {
        mutableStateOf(pregnancyTypes[0])
    }
    val singleton_or_twins_index = remember{
        mutableStateOf(0)
    }

    val fetus_1 = remember{
        mutableStateOf("")
    }

    val fetus_2 = remember{
        mutableStateOf("")
    }

    val examinationDate = remember {
        mutableStateOf(value = "")
    }

    val dayOfBirth = remember {
        mutableStateOf(value = "")
    }

    val height = remember {
        mutableStateOf(value = "")
    }

    val weight = remember {
        mutableStateOf(value = "")
    }

    val racialOrigin = listOf("White", "Black","South Asian","East Easian","Mixed")
    val expandedRacial = remember {
        mutableStateOf(false)
    }
    val racial_origin = remember {
        mutableStateOf(racialOrigin[0])
    }
    val racial_origin_index = remember{
        mutableStateOf(0)
    }

    val smoking = remember {
        mutableStateOf(2L)
    }

    val previous_preeclampsia = remember {
        mutableStateOf(2L)
    }

    val conceptionList = listOf("Spontenous", "Ovulatio drugs","In vitro fertilization")
    val expandedConception = remember {
        mutableStateOf(false)
    }

    val conception_method = remember {
        mutableStateOf(conceptionList[0])
    }
    val conception_method_index = remember{
        mutableStateOf(0)
    }

    val ch_hipertension = remember {
        mutableStateOf(2L)
    }

    val diabetes_type_1 = remember {
        mutableStateOf(2L)
    }

    val diabetes_type_2 = remember {
        mutableStateOf(2L)
    }

    val sle = remember {
        mutableStateOf(2L)
    }

    val aps = remember {
        mutableStateOf(2L)
    }

    val nulliparous = remember {
        mutableStateOf(2L)
    }

    val map = remember {
        mutableStateOf("")
    }

    val utapi = remember {
        mutableStateOf("")
    }

    val dateOfBiophysicalMeasurements = remember {
        mutableStateOf("")
    }

    val plgf = remember {
        mutableStateOf(2L)
    }

    val pappa = remember {
        mutableStateOf(2L)
    }

    val ga_age = 30f
    val inter_pregancy_interval = 0

    val last_pregnancy_pe = 2
    val last_pregnancy_delivery_weeks = 40
    val last_pregnancy_delivery_days =  0


    //Superior column that have a background color, so the space behind top bar is filed with color
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {
        //Secondary column that have a padding so as top bar is not sticky to the left and right edges
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow Back",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                },
                elevation = 5.dp,
                backgroundColor = (MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp))
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // #1 row of data - Pregnancy type and dating
                item {
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Basic Information",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //Pregnancy Type
                            ExposedDropdownMenuBox(
                                expanded = expanded.value,
                                onExpandedChange = {
                                    expanded.value = !expanded.value
                                },
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                TextField(
                                    value = pregnancyTypes[singleton_or_twins_index.value],
                                    onValueChange = {
                                        singleton_or_twins_index.value = pregnancyTypes.indexOf(it)
                                    },
                                    label = { Text(text = "Pregnancy Type", color = MaterialTheme.colorScheme.onPrimaryContainer)},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded.value
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    ),
                                    readOnly = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    ),
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false }) {
                                    pregnancyTypes.forEachIndexed{index,selectionOption ->
                                        DropdownMenuItem(
                                            onClick = {
                                                singleton_or_twins_index.value = index
                                                expanded.value = false
                                            }) {
                                            Text(text = selectionOption)
                                        }
                                    }
                                }
                            }

                            //Fetal crown-rump length
                            textInput(
                                text = fetus_1.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                        fetus_1.value = it
                                        fetus_2.value = it
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Fetal crown-rump length [mm]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )



                            //Examination Date
                            textInput(
                                text = examinationDate.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                    if (it.all { char ->
                                            char.isDigit() || char == '-'
                                        })
                                        examinationDate.value = it.take(10)
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Examination date [dd-mm-yyyy]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // #2 row of data - Maternal characteristics
                item {
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Maternal characteristics",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //Date of birth
                            textInput(
                                text = dayOfBirth.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                    if (it.all { char ->
                                            char.isDigit() || char == '-'
                                        })
                                        dayOfBirth.value = it.take(10)
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Expected date of birth [dd-mm-yyyy]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            //Height
                            textInput(
                                text = height.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                        height.value = it
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Height [cm]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            //Weight
                            textInput(
                                text = weight.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                        weight.value = it
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Weight [kg]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            //Racial origin
                            ExposedDropdownMenuBox(
                                expanded = expandedRacial.value,
                                onExpandedChange = {
                                    expandedRacial.value = !expandedRacial.value
                                },
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                TextField(
                                    value = racialOrigin[racial_origin_index.value],
                                    onValueChange = {
                                        racial_origin_index.value = racialOrigin.indexOf(it)
                                    },
                                    label = { Text(text = "Racial origin", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedRacial.value
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    readOnly = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedRacial.value,
                                    onDismissRequest = { expandedRacial.value = false }) {
                                    racialOrigin.forEachIndexed {index, selectionOption ->
                                        DropdownMenuItem(
                                            onClick = {
                                                racial_origin_index.value = index
                                                expandedRacial.value = false
                                            }) {
                                            Text(text = selectionOption)
                                        }
                                    }
                                }
                            }

                            //Smoking during pregnancy
                            Text(text = "Have you smoked during pregnancy?", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = smoking.value == 1L,
                                    onCheckedChange = {
                                        smoking.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = smoking.value == 2L,
                                    onCheckedChange = {
                                        smoking.value = if(it) 2 else 1
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //Mother of the patient had PE

                            Text(text = "Have your mather had PE?", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = previous_preeclampsia.value == 1L,
                                    onCheckedChange = {
                                        previous_preeclampsia.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes", color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = previous_preeclampsia.value == 2L,
                                    onCheckedChange = {
                                        previous_preeclampsia.value = if(it) 2 else 1
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                                )
                                Text(text = "No", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }


                            //Conception method
                            ExposedDropdownMenuBox(
                                expanded = expandedConception.value,
                                onExpandedChange = {
                                    expandedConception.value = !expandedConception.value
                                },
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                TextField(
                                    value = conceptionList[conception_method_index.value],
                                    onValueChange = {
                                        conception_method_index.value = conceptionList.indexOf(it)
                                    },
                                    label = { Text(text = "Conception method", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedConception.value
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    readOnly = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedConception.value,
                                    onDismissRequest = { expandedConception.value = false }) {
                                    conceptionList.forEachIndexed {index, selectionOption ->
                                        DropdownMenuItem(
                                            onClick = {
                                                conception_method_index.value = index
                                                expandedConception.value = false
                                            }) {
                                            Text(text = selectionOption)
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // #3 row of data - Medical history
                item {
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Medical history",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 40.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //Chronic hypertension
                            Text(text = "Do you have chronic hypertension?", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                Checkbox(
                                    checked = ch_hipertension.value == 1L,
                                    onCheckedChange = {
                                        ch_hipertension.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = ch_hipertension.value == 2L,
                                    onCheckedChange = {
                                        ch_hipertension.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //Diabetes type I
                            Text(text = "Do you have diabetes type I?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                Checkbox(
                                    checked = diabetes_type_1.value == 1L,
                                    onCheckedChange = {
                                        diabetes_type_1.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = diabetes_type_1.value == 2L,
                                    onCheckedChange = {
                                        diabetes_type_1.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //Diabetes type II
                            Text(text = "Do you have diabetes type II?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                Checkbox(
                                    checked = diabetes_type_2.value == 1L,
                                    onCheckedChange = {
                                        diabetes_type_2.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = diabetes_type_2.value == 2L,
                                    onCheckedChange = {
                                        diabetes_type_2.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //Systemic lupus erythematosus
                            Text(text = "Do you have lupus erythematosus?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                Checkbox(
                                    checked = sle.value == 1L,
                                    onCheckedChange = {
                                        sle.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = sle.value == 2L,
                                    onCheckedChange = {
                                        sle.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //Anti-phospholipid syndrome
                            Text(text = "Do you have anti-phospholipid syndrome?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                            ) {
                                Checkbox(
                                    checked = aps.value == 1L,
                                    onCheckedChange = {
                                        aps.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = aps.value == 2L,
                                    onCheckedChange = {
                                        aps.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // #4 row of data - Obstetric history
                item {
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Obstetric history",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 40.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //Nulliparous or Parous
                            Text(text = "Did you have at lest one pregnancy in less than 24 weeks?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(all = 20.dp)
                            ) {
                                Checkbox(
                                    checked = nulliparous.value == 1L,
                                    onCheckedChange = {
                                        nulliparous.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = nulliparous.value == 2L,
                                    onCheckedChange = {
                                        nulliparous.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // #5 row of data - Biophysical measurements
                item{
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Biophysical measurements",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //Mean arterial pressure
                            textInput(
                                text = map.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                    if (it.all { char ->
                                            char.isDigit() || char == '.'
                                        })
                                        map.value = it.take(5)
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Mean arterial pressure [mm]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            //Mean uterine artery
                            textInput(
                                text = utapi.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                    if (it.all { char ->
                                            char.isDigit() || char == '.'
                                        })
                                        utapi.value = it.take(5)
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Mean uterine artery PI",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            //Date of measurements
                            textInput(
                                text = dateOfBiophysicalMeasurements.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp),
                                onTextChange = {
                                    if (it.all { char ->
                                            char.isDigit() || char == '-'
                                        })
                                        dateOfBiophysicalMeasurements.value = it.take(10)
                                },
                                keyboard = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                label = "Date of last measurements [dd-mm-yyyy]",
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // #6 row of data - Biochemical measurements
                item{
                    Card(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(10.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        Text(
                            text = "Biochemical measurements",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 40.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            //PLGF
                            Text(text = "Did you last measurements included PLGF serum?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(all = 20.dp)
                            ) {
                                Checkbox(
                                    checked = plgf.value == 1L,
                                    onCheckedChange = {
                                        plgf.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = plgf.value == 2L,
                                    onCheckedChange = {
                                        plgf.value = if(it) 2 else 1
                                    },
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            //PAPP-A
                            Text(text = "Did you last measurements included PAPP-A serum?",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(all = 20.dp)
                            ) {
                                Checkbox(
                                    checked = pappa.value ==1L ,
                                    onCheckedChange = {
                                        pappa.value = if(it) 1 else 2
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.inversePrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(text = "Yes",color = MaterialTheme.colorScheme.onPrimaryContainer)

                                Checkbox(
                                    checked = pappa.value == 2L,
                                    onCheckedChange = {
                                        pappa.value = if(it) 2 else 1
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.error,
                                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                )
                                Text(text = "No",color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                        }
                    }
                }
                //Divider
                item {
                    Divider(
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                // Button for saving
                item {
                    addButton(
                        modifier = Modifier.padding(30.dp),
                        text = "Save data",
                        onClick = {
                            if(
                                singleton_or_twins.value.isNotEmpty() &&
                                weight.value.isNotEmpty() &&
                                height.value.isNotEmpty() &&
                                fetus_1.value.isNotEmpty() &&
                                examinationDate.value.isNotEmpty() &&
                                dayOfBirth.value.isNotEmpty() &&
                                weight.value.isNotEmpty() &&
                                racial_origin.value.isNotEmpty() &&
                                conception_method.value.isNotEmpty() &&
                                map.value.isNotEmpty() &&
                                dateOfBiophysicalMeasurements.value.isNotEmpty()
                            ){
                                saveDataToJson(
                                    context, data = MaternalData(
                                        singleton_or_twins = singleton_or_twins_index.value + 1L,
                                        fetus_1 = fetus_1.value.toLong(),
                                        fetus_2 = fetus_2.value.toLong(),
                                        examinationDate = LocalDate.parse(examinationDate.value, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toEpochDay().toLong(),
                                        dayOfBirth = LocalDate.parse(dayOfBirth.value, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toEpochDay().toLong(),
                                        height = height.value.toLong(),
                                        weight = weight.value.toLong(),
                                        racial_origin = racial_origin_index.value + 1L,
                                        smoking = smoking.value,
                                        previous_preeclampsia = previous_preeclampsia.value,
                                        conception_method =conception_method_index.value +1L,
                                        ch_hipertension = ch_hipertension.value,
                                        diabetes_type_1 = diabetes_type_1.value,
                                        diabetes_type_2 = diabetes_type_2.value,
                                        sle = sle .value,
                                        aps = aps.value,
                                        nulliparous = nulliparous.value,
                                        last_pregnancy_pe = last_pregnancy_pe,
                                        last_pregnancy_delivery_weeks = last_pregnancy_delivery_weeks,
                                        last_pregnancy_delivery_days =  last_pregnancy_delivery_days,
                                        map =  map.value.toFloat(),
                                        dateOfBiophysicalMeasurements =  LocalDate.parse(dateOfBiophysicalMeasurements.value, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toEpochDay().toLong(),
                                        plgf =  plgf.value,
                                        pappa = pappa.value,
                                        ga_age = ga_age.toLong(),
                                        inter_pregancy_interval = inter_pregancy_interval,
                                        utapi = utapi.value.toFloat()
                                    ))
                                navController.navigate(route = Screens.MainScreen.name)
                            }else{
                                Toast.makeText(context, "Data not saved, complete all data", Toast.LENGTH_SHORT).show()
                            }

                        }
                    )
                }

            }

        }
    }

}

private fun saveDataToJson(context: Context, data: MaternalData) {
    val json = Gson().toJson(data)

    // Request write external storage permission
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        return
    }

    // Get the downloads folder path
    val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    // Create the directory if it doesn't exist
    if (!folder.exists()) {
        folder.mkdir()
    }

    // Find the next available file name
    var i = 1
    var file = File(folder, "data$i.json")
    while (file.exists()) {
        i++
        file = File(folder, "data$i.json")
    }

    // Write data to the file
    try {
        file.writeText(json)
        Toast.makeText(context, "Data saved to file successfully!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error saving data to file!", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }

    // Upload the file to Firebase Storage
    val storageRef = Firebase.storage.reference
    val uploadTask = storageRef.child(file.name).putFile(file.toUri())

    uploadTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "File uploaded to Firebase Storage successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error uploading file to Firebase Storage!", Toast.LENGTH_SHORT).show()
            task.exception?.printStackTrace()
        }
    }
}

