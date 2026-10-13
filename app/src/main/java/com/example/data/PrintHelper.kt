package com.example.data

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

object PrintHelper {

    /**
     * Prints an HTML document using the Android PrintManager and a background WebView.
     */
    fun printHtml(context: Context, htmlContent: String, jobTitle: String) {
        try {
            val webView = WebView(context)
            webView.settings.javaScriptEnabled = false
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
                    if (printManager != null && view != null) {
                        val printAdapter = view.createPrintDocumentAdapter(jobTitle)
                        val printAttributes = PrintAttributes.Builder()
                            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                            .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                            .build()
                        printManager.print(jobTitle, printAdapter, printAttributes)
                    } else {
                        Toast.makeText(context, "تعذر الوصول لخدمة الطباعة في الجهاز", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Load HTML with UTF-8 encoding
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
        } catch (e: Exception) {
            Toast.makeText(context, "خطأ أثناء إرسال أمر الطباعة: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Builds HTML formatted document for printing a lesson
     */
    fun generateLessonHtml(lesson: Lesson, subjectName: String): String {
        return """
        <!DOCTYPE html>
        <html dir="rtl" lang="ar">
        <head>
            <meta charset="UTF-8">
            <title>${lesson.title} - Yassin Study 2027</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Arial, sans-serif;
                    padding: 24px;
                    color: #1e293b;
                    line-height: 1.8;
                }
                .header {
                    border-bottom: 2px solid #4f46e5;
                    padding-bottom: 12px;
                    margin-bottom: 20px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
                .app-badge {
                    background: #4f46e5;
                    color: #ffffff;
                    padding: 6px 14px;
                    border-radius: 20px;
                    font-weight: bold;
                    font-size: 14px;
                }
                h1 { color: #1e1b4b; font-size: 24px; margin: 4px 0; }
                h2 { color: #4338ca; font-size: 18px; border-right: 4px solid #6366f1; padding-right: 8px; margin-top: 24px; }
                .box {
                    background: #f8fafc;
                    border: 1px solid #e2e8f0;
                    border-radius: 8px;
                    padding: 14px;
                    margin: 12px 0;
                }
                .rule {
                    background: #eef2ff;
                    border-right: 4px solid #4f46e5;
                    padding: 10px 14px;
                    margin: 8px 0;
                    font-weight: bold;
                }
                .footer {
                    margin-top: 40px;
                    text-align: center;
                    font-size: 12px;
                    color: #64748b;
                    border-top: 1px dashed #cbd5e1;
                    padding-top: 12px;
                }
                @media print {
                    body { padding: 0; }
                }
            </style>
        </head>
        <body>
            <div class="header">
                <div>
                    <span class="app-badge">Yassin Study 2027</span>
                    <h1>${lesson.title}</h1>
                    <p style="margin: 4px 0; color: #64748b;">المادة: $subjectName | الصف الثالث الإعدادي (2026/2027)</p>
                </div>
            </div>

            <h2>📖 الشرح المبسط</h2>
            <div class="box">${lesson.simpleSummary}</div>

            <h2>🔍 الشرح التفصيلي</h2>
            <div class="box">${lesson.detailedExplanation.replace("\n", "<br>")}</div>

            <h2>⭐ أهم النقاط والملخص</h2>
            <ul>
                ${lesson.keyPoints.joinToString("") { "<li>$it</li>" }}
            </ul>

            ${if (lesson.formulas.isNotEmpty()) {
                "<h2>📐 القوانين الرياضية والفيزيائية</h2>" +
                lesson.formulas.joinToString("") {
                    "<div class='rule'>${it.name}: <code>${it.formula}</code> (${it.unit})<br><small style='color: #475569;'>${it.explanation}</small></div>"
                }
            } else ""}

            ${if (lesson.definitions.isNotEmpty()) {
                "<h2>📝 التعريفات والمصطلحات الأساسية</h2>" +
                lesson.definitions.joinToString("") {
                    "<div class='box'><strong>${it.term}:</strong> ${it.meaning} ${if (it.note.isNotEmpty()) "<br><small style='color: #6366f1;'>ملاحظة: ${it.note}</small>" else ""}</div>"
                }
            } else ""}

            ${if (lesson.homework.isNotEmpty()) {
                "<h2>✍️ واجب الدرس</h2><div class='box' style='background: #fffbeb; border-color: #fde68a;'>${lesson.homework}</div>"
            } else ""}

            <div class="footer">
                تمت الطباعة بواسطة منصة Yassin Study 2027 التعليمية | الصف الثالث الإعدادي | تطوير: ياسين وائل نجيب
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    /**
     * Builds HTML formatted document for printing an exam (with or without answers)
     */
    fun generateExamHtml(
        examTitle: String,
        subjectName: String,
        questions: List<BankQuestion>,
        includeAnswers: Boolean
    ): String {
        return """
        <!DOCTYPE html>
        <html dir="rtl" lang="ar">
        <head>
            <meta charset="UTF-8">
            <title>$examTitle - Yassin Study 2027</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Arial, sans-serif;
                    padding: 24px;
                    color: #0f172a;
                    line-height: 1.8;
                }
                .header-table {
                    width: 100%;
                    border: 2px solid #0f172a;
                    margin-bottom: 24px;
                    border-collapse: collapse;
                }
                .header-table td {
                    padding: 10px;
                    border: 1px solid #94a3b8;
                    text-align: center;
                }
                .q-block {
                    margin-bottom: 20px;
                    padding: 12px;
                    border-bottom: 1px dashed #cbd5e1;
                    page-break-inside: avoid;
                }
                .q-num {
                    display: inline-block;
                    background: #4f46e5;
                    color: white;
                    border-radius: 50%;
                    width: 26px;
                    height: 26px;
                    text-align: center;
                    line-height: 26px;
                    font-weight: bold;
                    margin-left: 8px;
                }
                .options-grid {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 10px;
                    margin: 10px 0;
                    padding-right: 32px;
                }
                .opt-item {
                    border: 1px solid #cbd5e1;
                    padding: 6px 12px;
                    border-radius: 6px;
                    background: #f8fafc;
                }
                .answer-box {
                    background: #ecfdf5;
                    border: 1px solid #10b981;
                    border-radius: 6px;
                    padding: 8px 12px;
                    margin-top: 8px;
                    color: #065f46;
                    font-weight: bold;
                }
                .footer {
                    margin-top: 30px;
                    text-align: center;
                    font-size: 12px;
                    color: #64748b;
                }
            </style>
        </head>
        <body>
            <table class="header-table">
                <tr>
                    <td style="width: 30%;">
                        <strong>وزارة التربية والتعليم</strong><br>
                        امتحانات الشهادة الإعدادية 2026/2027<br>
                        منصة Yassin Study 2027
                    </td>
                    <td style="width: 40%; font-size: 18px;">
                        <strong>$examTitle</strong><br>
                        المادة: $subjectName<br>
                        <span style="color: ${if (includeAnswers) "#059669" else "#dc2626"}; font-weight: bold;">
                            ${if (includeAnswers) "【 نموذج الإجابة الرسمي مع الشرح 】" else "【 ورقة الأسئلة للطلاب 】"}
                        </span>
                    </td>
                    <td style="width: 30%;">
                        اسم الطالب: ......................<br>
                        رقم الجلوس: ......................<br>
                        الزمن: ساعتان
                    </td>
                </tr>
            </table>

            ${questions.mapIndexed { index, q ->
                """
                <div class="q-block">
                    <p style="font-size: 16px; margin: 0 0 8px 0;">
                        <span class="q-num">${index + 1}</span>
                        <strong>${q.question}</strong>
                        <span style="font-size: 12px; color: #64748b; margin-right: 8px;">(${q.type.title} - ${q.difficulty.title})</span>
                    </p>
                    ${if (q.options.isNotEmpty()) {
                        """<div class="options-grid">
                            ${q.options.mapIndexed { optIdx, opt ->
                                val letter = listOf("أ", "ب", "جـ", "د").getOrElse(optIdx) { "${optIdx + 1}" }
                                "<div class='opt-item'>($letter) $opt</div>"
                            }.joinToString("")}
                        </div>"""
                    } else if (!includeAnswers) {
                        "<div style='height: 40px; border-bottom: 1px dotted #94a3b8; margin-top: 12px;'></div>"
                    } else ""}

                    ${if (includeAnswers) {
                        """
                        <div class="answer-box">
                            ✓ الإجابة النموذجية: ${q.answer}<br>
                            <span style="font-weight: normal; color: #047857; font-size: 13px;">تفسير الإجابة: ${q.explanation}</span>
                        </div>
                        """
                    } else ""}
                </div>
                """
            }.joinToString("")}

            <div class="footer">
                ${if (!includeAnswers) "« انتهت الأسئلة - مع أطيب التمنيات بالنجاح والتفوق »" else "« نموذج إجابة معتمد ومفصل »"}
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    /**
     * Builds HTML formatted document for printing a book or selected pages
     */
    fun generateBookPagesHtml(book: StudyBook, startPage: Int = 1, endPage: Int = book.pageCount): String {
        val selectedPages = book.pages.filter { it.pageNumber in startPage..endPage }
        return """
        <!DOCTYPE html>
        <html dir="rtl" lang="ar">
        <head>
            <meta charset="UTF-8">
            <title>${book.title} - صفحات $startPage إلى $endPage</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Arial, sans-serif;
                    padding: 24px;
                    color: #1e293b;
                    line-height: 1.8;
                }
                .book-header {
                    text-align: center;
                    border-bottom: 2px solid #4f46e5;
                    padding-bottom: 16px;
                    margin-bottom: 24px;
                }
                .page-container {
                    border: 1px solid #cbd5e1;
                    border-radius: 8px;
                    padding: 24px;
                    margin-bottom: 30px;
                    page-break-after: always;
                    background: #ffffff;
                }
                .page-header {
                    display: flex;
                    justify-content: space-between;
                    border-bottom: 1px solid #e2e8f0;
                    padding-bottom: 8px;
                    font-size: 13px;
                    color: #64748b;
                }
                .page-body {
                    margin: 16px 0;
                    font-size: 16px;
                }
                .key-takeaway {
                    background: #eff6ff;
                    border-right: 4px solid #3b82f6;
                    padding: 10px 14px;
                    margin-top: 16px;
                    font-size: 14px;
                }
            </style>
        </head>
        <body>
            <div class="book-header">
                <h1 style="color: #1e1b4b; margin: 0;">${book.title}</h1>
                <p style="color: #64748b; margin: 6px 0;">${book.author} | عدد الصفحات المطبوعة: ${selectedPages.size}</p>
            </div>

            ${selectedPages.map { page ->
                """
                <div class="page-container">
                    <div class="page-header">
                        <span>${page.chapterTitle}</span>
                        <span>صفحة: ${page.pageNumber}</span>
                    </div>
                    <div class="page-body">
                        ${page.content.replace("\n", "<br>")}
                    </div>
                    ${if (page.keyTakeaway.isNotEmpty()) {
                        "<div class='key-takeaway'><strong>💡 الفكرة الأساسية:</strong> ${page.keyTakeaway}</div>"
                    } else ""}
                </div>
                """
            }.joinToString("")}
        </body>
        </html>
        """.trimIndent()
    }
}
