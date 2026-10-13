package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumDataSource
import com.example.data.ExperimentType
import com.example.data.ScienceExperiment
import com.example.ui.components.GlassCard
import kotlin.math.roundToInt

@Composable
fun ScienceLabScreen() {
    val experiments = CurriculumDataSource.scienceExperiments
    var selectedExp by remember { mutableStateOf(experiments.first()) }
    var isInActiveMode by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("science_lab_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Experiments Selector
        item {
            Text(
                text = "🔬 اختر التجربة المعملية",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(experiments) { exp ->
                    val isSelected = selectedExp.id == exp.id
                    GlassCard(
                        modifier = Modifier.clickable {
                            selectedExp = exp
                            isInActiveMode = false
                        },
                        backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else null,
                        borderBrush = if (isSelected) Brush.horizontalGradient(
                            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                        ) else null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = exp.title.take(24) + "...",
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Experiment Overview Card
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = selectedExp.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "🎯 الهدف من التجربة: ${selectedExp.target}",
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🧰 الأدوات المستخدمة:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = selectedExp.tools.joinToString(" • "),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { isInActiveMode = !isInActiveMode },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInActiveMode) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (isInActiveMode) Icons.Default.Refresh else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isInActiveMode) "إعادة ضبط المحاكاة التفاعلية" else "ابدأ التجربة التفاعلية الآن 🚀",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Interactive Simulation Workspace
        item {
            Text(
                text = "⚡ لوحة المحاكاة الحية",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))

            when (selectedExp.type) {
                ExperimentType.SPEED_MOTION -> SpeedMotionSimulation()
                ExperimentType.OPTICS_MIRRORS -> OpticsMirrorSimulation()
                ExperimentType.OHMS_LAW -> OhmsLawSimulation()
                ExperimentType.MENDEL_GENETICS -> MendelGeneticsSimulation()
            }
        }

        // Scientific Explanation & Steps
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "📋 خطوات التنفيذ والملاحظة:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    selectedExp.steps.forEachIndexed { i, step ->
                        Text(
                            text = "${i + 1}) $step",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "🔬 التفسير العلمي والاستنتاج:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF10B981)
                    )
                    Text(
                        text = selectedExp.scientificExplanation,
                        fontSize = 13.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

// 1. Simulation: Speed & Motion
@Composable
fun SpeedMotionSimulation() {
    var distance by remember { mutableFloatStateOf(120f) }
    var time by remember { mutableFloatStateOf(6f) }
    var isMoving by remember { mutableStateOf(false) }

    val speed = distance / time
    val carProgress by animateFloatAsState(
        targetValue = if (isMoving) 1f else 0f,
        animationSpec = tween(durationMillis = (time * 600).roundToInt(), easing = LinearEasing),
        label = "car_progress"
    )

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "المسافة (ف): ${distance.roundToInt()} متر", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(text = "الزمن (ز): ${time.roundToInt()} ثوانٍ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Slider(
                value = distance,
                onValueChange = {
                    distance = it
                    isMoving = false
                },
                valueRange = 20f..300f,
                colors = SliderDefaults.colors(thumbColor = Color(0xFF6366F1), activeTrackColor = Color(0xFF6366F1))
            )

            Slider(
                value = time,
                onValueChange = {
                    time = it
                    isMoving = false
                },
                valueRange = 2f..20f,
                colors = SliderDefaults.colors(thumbColor = Color(0xFF38BDF8), activeTrackColor = Color(0xFF38BDF8))
            )

            // Live Canvas Track
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val roadY = size.height * 0.65f
                    // Draw Road
                    drawLine(
                        color = Color(0xFF334155),
                        start = Offset(20f, roadY),
                        end = Offset(size.width - 20f, roadY),
                        strokeWidth = 6f
                    )
                    // Road marks
                    for (x in 30..(size.width - 30).toInt() step 40) {
                        drawLine(
                            color = Color(0xFFFACC15),
                            start = Offset(x.toFloat(), roadY),
                            end = Offset(x.toFloat() + 15f, roadY),
                            strokeWidth = 3f
                        )
                    }

                    // Car position
                    val carStartX = 20f
                    val carEndX = size.width - 60f
                    val currentCarX = carStartX + (carEndX - carStartX) * carProgress

                    // Draw Car body
                    drawRoundRect(
                        color = Color(0xFFEF4444),
                        topLeft = Offset(currentCarX, roadY - 24f),
                        size = Size(40f, 18f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                    )
                    // Car wheels
                    drawCircle(color = Color.Black, radius = 5f, center = Offset(currentCarX + 8f, roadY - 4f))
                    drawCircle(color = Color.Black, radius = 5f, center = Offset(currentCarX + 32f, roadY - 4f))
                }

                Text(
                    text = "السرعة المحسوبة: ${"%.1f".format(speed)} م/ث (${"%.1f".format(speed * 3.6f)} كم/س)",
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { isMoving = !isMoving },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (isMoving) "إيقاف الحركة" else "انطلاق السيارة 🏎️")
            }
        }
    }
}

// 2. Simulation: Optics & Concave Mirror
@Composable
fun OpticsMirrorSimulation() {
    var objectDist by remember { mutableFloatStateOf(25f) } // Focus is 10, Center is 20

    val focus = 10f
    val center = 20f

    val imageState = when {
        objectDist > center -> "حقيقية • مقلوبة • مصغرة (بين ب و م)"
        objectDist == center -> "حقيقية • مقلوبة • مساوية للجسم (عند م)"
        objectDist > focus -> "حقيقية • مقلوبة • مكبرة (بعد م)"
        objectDist == focus -> "لا تتكون صورة (الأشعة متوازية في المالانهاية)"
        else -> "تقديرية • معتدلة • مكبرة (خلف المرآة)"
    }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                text = "بعد الجسم عن المرآة: ${objectDist.roundToInt()} سم (البؤرة ب = 10 سم ، المركز م = 20 سم)",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Slider(
                value = objectDist,
                onValueChange = { objectDist = it },
                valueRange = 4f..35f,
                colors = SliderDefaults.colors(thumbColor = Color(0xFF9333EA))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val midY = size.height / 2
                    val mirrorX = size.width - 30f

                    // Principal Axis
                    drawLine(
                        color = Color(0xFF475569),
                        start = Offset(10f, midY),
                        end = Offset(size.width - 10f, midY),
                        strokeWidth = 2f
                    )

                    // Concave mirror arc
                    val mirrorPath = Path().apply {
                        moveTo(mirrorX, midY - 50f)
                        quadraticTo(mirrorX - 25f, midY, mirrorX, midY + 50f)
                    }
                    drawPath(mirrorPath, color = Color(0xFF38BDF8), style = Stroke(width = 4f))

                    // Pole (P), Focus (F), Center (C)
                    val scale = 6f
                    val focusX = mirrorX - (focus * scale)
                    val centerX = mirrorX - (center * scale)

                    drawCircle(color = Color.White, radius = 4f, center = Offset(mirrorX, midY))
                    drawCircle(color = Color(0xFFFACC15), radius = 4f, center = Offset(focusX, midY))
                    drawCircle(color = Color(0xFF10B981), radius = 4f, center = Offset(centerX, midY))

                    // Object Candle
                    val objX = mirrorX - (objectDist * scale)
                    val objHeight = 35f
                    drawLine(
                        color = Color(0xFFEF4444),
                        start = Offset(objX, midY),
                        end = Offset(objX, midY - objHeight),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                    // Candle flame
                    drawCircle(color = Color(0xFFF97316), radius = 4f, center = Offset(objX, midY - objHeight - 4f))

                    // Ray 1: Parallel to axis then reflecting through focus
                    drawLine(
                        color = Color(0x99FACC15),
                        start = Offset(objX, midY - objHeight),
                        end = Offset(mirrorX - 8f, midY - objHeight),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0x99FACC15),
                        start = Offset(mirrorX - 8f, midY - objHeight),
                        end = Offset(focusX - 40f, midY + 40f),
                        strokeWidth = 2f
                    )
                }

                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Text(
                        text = "خواص الصورة: $imageState",
                        color = Color(0xFF34D399),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// 3. Simulation: Ohm's Law & Circuit
@Composable
fun OhmsLawSimulation() {
    var voltage by remember { mutableFloatStateOf(12f) }
    var resistance by remember { mutableFloatStateOf(4f) }

    val current = voltage / resistance
    val power = current * voltage

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "فرق الجهد (جـ): ${voltage.roundToInt()} فولت", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(text = "المقاومة (م): ${resistance.roundToInt()} أوم", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Slider(value = voltage, onValueChange = { voltage = it }, valueRange = 2f..24f)
            Slider(value = resistance, onValueChange = { resistance = it }, valueRange = 1f..20f)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Circuit Wire rectangle
                    val rectPath = Path().apply {
                        moveTo(w * 0.15f, h * 0.2f)
                        lineTo(w * 0.85f, h * 0.2f)
                        lineTo(w * 0.85f, h * 0.8f)
                        lineTo(w * 0.15f, h * 0.8f)
                        close()
                    }
                    drawPath(rectPath, color = Color(0xFF3B82F6), style = Stroke(width = 3f))

                    // Battery on left
                    drawRect(
                        color = Color(0xFF10B981),
                        topLeft = Offset(w * 0.13f, h * 0.42f),
                        size = Size(w * 0.04f, h * 0.16f)
                    )

                    // Resistor on bottom
                    drawRoundRect(
                        color = Color(0xFFD97706),
                        topLeft = Offset(w * 0.42f, h * 0.74f),
                        size = Size(w * 0.16f, h * 0.12f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                    )

                    // Glowing Bulb on top
                    val bulbCenter = Offset(w * 0.5f, h * 0.2f)
                    val glowAlpha = (power / 100f).coerceIn(0.1f, 0.9f)
                    drawCircle(color = Color(0xFFFDE047).copy(alpha = glowAlpha), radius = 22f, center = bulbCenter)
                    drawCircle(color = Color(0xFFF59E0B), radius = 10f, center = bulbCenter)
                }

                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Text(
                        text = "شدة التيار (ت) = جـ ÷ م = ${"%.2f".format(current)} أمبير",
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "القدرة المستهلكة: ${"%.1f".format(power)} واط (توهج المصباح)",
                        color = Color(0xFFFDE047),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

// 4. Simulation: Mendel Genetics Punnett Square
@Composable
fun MendelGeneticsSimulation() {
    var fatherGene by remember { mutableStateOf("Rr") } // R: Red/Purple (dominant), r: white (recessive)
    var motherGene by remember { mutableStateOf("rr") }

    val genes = listOf("RR (قرمزي نقي)", "Rr (قرمزي هجين)", "rr (أبيض متنحي)")

    // Calculate Punnett matrix
    val f1 = fatherGene[0]
    val f2 = fatherGene[1]
    val m1 = motherGene[0]
    val m2 = motherGene[1]

    val cell1 = "$f1$m1"
    val cell2 = "$f1$m2"
    val cell3 = "$f2$m1"
    val cell4 = "$f2$m2"

    val results = listOf(cell1, cell2, cell3, cell4)
    val redCount = results.count { it.contains('R') }
    val whiteCount = 4 - redCount

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "تزاوج نبات بسلة الخضر (لون الأزهار)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = "جينات الأب:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row {
                        listOf("RR", "Rr", "rr").forEach { g ->
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (fatherGene == g) Color(0xFF9333EA) else MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { fatherGene = g }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = g, color = if (fatherGene == g) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Column {
                    Text(text = "جينات الأم:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row {
                        listOf("RR", "Rr", "rr").forEach { g ->
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (motherGene == g) Color(0xFFEC4899) else MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { motherGene = g }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = g, color = if (motherGene == g) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Punnett Grid
            Text(text = "مربع بانيت (Punnett Square):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F172A))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PunnettCell(gene = cell1)
                    PunnettCell(gene = cell2)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PunnettCell(gene = cell3)
                    PunnettCell(gene = cell4)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "أزهار قرمزية (سائد): ${(redCount * 25)}%",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA855F7),
                    fontSize = 13.sp
                )
                Text(
                    text = "أزهار بيضاء (متنحٍ): ${(whiteCount * 25)}%",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38BDF8),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun PunnettCell(gene: String) {
    val isRed = gene.contains('R')
    Box(
        modifier = Modifier
            .size(70.dp, 45.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isRed) Color(0xFF6B21A8) else Color(0xFF1E293B))
            .border(1.dp, Color(0xFF475569), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = gene, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
            Text(text = if (isRed) "قرمزي" else "أبيض", fontSize = 10.sp, color = Color(0xFFE2E8F0))
        }
    }
}
