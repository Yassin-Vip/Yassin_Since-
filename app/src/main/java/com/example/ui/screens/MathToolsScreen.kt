package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

@Composable
fun MathToolsScreen() {
    var selectedToolIndex by remember { mutableIntStateOf(0) }
    val toolTabs = listOf("حل المعادلة التربيعية 📈", "الهندسة التحليلية 📐", "حساب المثلثات 🔺")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("math_tools_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedToolIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            toolTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedToolIndex == index,
                    onClick = { selectedToolIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedToolIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            when (selectedToolIndex) {
                0 -> item { QuadraticEquationSolver() }
                1 -> item { AnalyticGeometryCalculator() }
                2 -> item { TrigonometryExplorer() }
            }
        }
    }
}

// 1. Quadratic Equation Solver
@Composable
fun QuadraticEquationSolver() {
    var aStr by remember { mutableStateOf("1") }
    var bStr by remember { mutableStateOf("-5") }
    var cStr by remember { mutableStateOf("6") }

    val a = aStr.toFloatOrNull() ?: 1f
    val b = bStr.toFloatOrNull() ?: 0f
    val c = cStr.toFloatOrNull() ?: 0f

    val discriminant = (b * b) - (4 * a * c)

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                text = "حل معادلة الدرجة الثانية بالقانون العام:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "الصورة العامة: أ س² + ب س + جـ = 0",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = aStr,
                    onValueChange = { aStr = it },
                    label = { Text("أ (معامل س²)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = bStr,
                    onValueChange = { bStr = it },
                    label = { Text("ب (معامل س)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cStr,
                    onValueChange = { cStr = it },
                    label = { Text("جـ (الحد المطلق)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Step-by-step Solution
            Text(text = "📝 خطوات الحل بالتفصيل:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "1) حساب المميز (ب² - 4أ جـ):",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            Text(
                text = "Δ = ($b)² - 4($a)($c) = ${(b * b)} - ${(4 * a * c)} = $discriminant",
                color = Color(0xFF6366F1),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
            val nature = when {
                discriminant > 0 -> "المميز موجب (> 0): يوجد حلان حقيقيان مختلفان (يقطع محور السينات في نقطتين)."
                discriminant == 0f -> "المميز صفر (= 0): يوجد حل حقيقي وحيد مكرر (يمس محور السينات في نقطة الرأس)."
                else -> "المميز سالب (< 0): لا توجد جذور حقيقية في ح (مجموعة الحل فاي ∅)."
            }
            Text(text = "نوع الجذرين: $nature", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(8.dp))
            if (discriminant >= 0 && a != 0f) {
                val sqrtDisc = sqrt(discriminant)
                val root1 = (-b + sqrtDisc) / (2 * a)
                val root2 = (-b - sqrtDisc) / (2 * a)

                Text(
                    text = "2) تطبيق القانون العام: س = (-ب ± √المميز) ÷ 2أ",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "س₁ = [${-b} + ${"%.2f".format(sqrtDisc)}] ÷ ${2 * a} = ${"%.2f".format(root1)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981)
                )
                Text(
                    text = "س₂ = [${-b} - ${"%.2f".format(sqrtDisc)}] ÷ ${2 * a} = ${"%.2f".format(root2)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFD1FAE5))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "مجموعة الحل م.ح = { ${"%.2f".format(root1)} ، ${"%.2f".format(root2)} }",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF065F46)
                    )
                }
            }

            // Interactive Parabola Graph Canvas
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "📊 التمثيل البياني للمنحنى:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(6.dp))

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
                    val originX = w / 2
                    val originY = h / 2

                    // Axes
                    drawLine(Color(0xFF334155), Offset(0f, originY), Offset(w, originY), 2f)
                    drawLine(Color(0xFF334155), Offset(originX, 0f), Offset(originX, h), 2f)

                    // Draw Parabola curve: f(x) = a x^2 + b x + c
                    val scale = 14f
                    val path = Path()
                    var first = true

                    for (xPix in 0..w.toInt() step 4) {
                        val mathX = (xPix - originX) / scale
                        val mathY = (a * mathX * mathX) + (b * mathX) + c
                        val yPix = originY - (mathY * scale)

                        if (yPix in -50f..(h + 50f)) {
                            if (first) {
                                path.moveTo(xPix.toFloat(), yPix)
                                first = false
                            } else {
                                path.lineTo(xPix.toFloat(), yPix)
                            }
                        }
                    }
                    drawPath(path, color = Color(0xFF38BDF8), style = Stroke(width = 3f))
                }
            }
        }
    }
}

// 2. Analytic Geometry Calculator
@Composable
fun AnalyticGeometryCalculator() {
    var x1Str by remember { mutableStateOf("1") }
    var y1Str by remember { mutableStateOf("2") }
    var x2Str by remember { mutableStateOf("4") }
    var y2Str by remember { mutableStateOf("6") }

    val x1 = x1Str.toFloatOrNull() ?: 1f
    val y1 = y1Str.toFloatOrNull() ?: 2f
    val x2 = x2Str.toFloatOrNull() ?: 4f
    val y2 = y2Str.toFloatOrNull() ?: 6f

    val dx = x2 - x1
    val dy = y2 - y1
    val distance = sqrt((dx * dx) + (dy * dy))
    val slope = if (dx != 0f) dy / dx else null
    val midX = (x1 + x2) / 2
    val midY = (y1 + y2) / 2

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                text = "الهندسة التحليلية: البعد والميل والمنتصف",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = x1Str,
                    onValueChange = { x1Str = it },
                    label = { Text("س₁ (نقطة أ)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = y1Str,
                    onValueChange = { y1Str = it },
                    label = { Text("ص₁ (نقطة أ)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = x2Str,
                    onValueChange = { x2Str = it },
                    label = { Text("س₂ (نقطة ب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = y2Str,
                    onValueChange = { y2Str = it },
                    label = { Text("ص₂ (نقطة ب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "📐 النتائج مع خطوات الحل:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(6.dp))

            // Distance
            Text(text = "1) البعد بين النقطتين أ و ب:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(
                text = "ف = √[(س₂ - س₁)² + (ص₂ - ص₁)²] = √[($dx)² + ($dy)²] = √${(dx*dx + dy*dy)} = ${"%.2f".format(distance)} وحدة طول",
                color = Color(0xFF6366F1),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            // Slope
            Text(text = "2) ميل المستقيم أ ب:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(
                text = if (slope != null) "م = (ص₂ - ص₁) ÷ (س₂ - س₁) = $dy ÷ $dx = ${"%.2f".format(slope)}" else "الميل غير معرف (المستقيم رأسي يوازي محور الصادات)",
                color = Color(0xFF9333EA),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            // Midpoint
            Text(text = "3) إحداثيا نقطة المنتصف م:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(
                text = "م = [ (س₁ + س₂)/2 ، (ص₁ + ص₂)/2 ] = [ $midX ، $midY ]",
                color = Color(0xFF10B981),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// 3. Trigonometry Explorer
@Composable
fun TrigonometryExplorer() {
    var selectedAngle by remember { mutableFloatStateOf(30f) }

    val rad = Math.toRadians(selectedAngle.toDouble())
    val sinVal = sin(rad)
    val cosVal = cos(rad)
    val tanVal = tan(rad)

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                text = "حساب المثلثات: النسب المثلثية للزاوية الحادة",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "اختر زاوية خاصة:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(30f, 45f, 60f).forEach { angle ->
                    Button(
                        onClick = { selectedAngle = angle },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAngle == angle) Color(0xFF6366F1) else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (selectedAngle == angle) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("${angle.toInt()}°")
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "جا (${selectedAngle.toInt()}°) = المقابل ÷ الوتر = ${"%.3f".format(sinVal)}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF6366F1)
            )
            Text(
                text = "جتا (${selectedAngle.toInt()}°) = المجاور ÷ الوتر = ${"%.3f".format(cosVal)}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF9333EA)
            )
            Text(
                text = "ظا (${selectedAngle.toInt()}°) = المقابل ÷ المجاور = ${"%.3f".format(tanVal)}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF0284C7)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "العلاقات الذهبية:\n• جا² هـ + جتا² هـ = ${(sinVal * sinVal + cosVal * cosVal).toInt()}\n• ظا هـ = جا هـ ÷ جتا هـ\n• جا 30° = جتا 60° = 1/2",
                fontSize = 12.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
