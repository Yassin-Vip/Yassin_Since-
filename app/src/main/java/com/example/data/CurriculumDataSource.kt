package com.example.data

object CurriculumDataSource {

    val subjects: List<Subject> = listOf(
        Subject(
            id = "science",
            name = "العلوم",
            englishName = "Science",
            iconName = "Science",
            colorHex = 0xFF9333EA,
            description = "القوى والحركة، الضوء والبصريات، الكون والفلك، والتكاثر وعلم الوراثة",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "sci_u1",
                    subjectId = "science",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: القوى والحركة",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "sci_l1",
                            unitId = "sci_u1",
                            subjectId = "science",
                            term = Term.TERM_1,
                            title = "الدرس الأول: الحركة في اتجاه واحد والسرعة",
                            order = 1,
                            simpleSummary = "الحركة هي تغير موضع الجسم بمرور الزمن بالنسبة لموضع جسم آخر ثابت. وأبسط أنواع الحركة هي الحركة في خط مستقيم في اتجاه واحد مثل حركة القطار أو المترو. تقاس السرعة بالمسافة المقطوعة خلال وحدة الزمن: السرعة = المسافة ÷ الزمن.",
                            detailedExplanation = "تعتبر الحركة من المفاهيم الفيزيائية الأساسية. إذا تحرك جسم وقطع مسافات متساوية في أزمنة متساوية يقال إنه يتحرك بسرعة منتظمة (ثابتة). وإذا قطع مسافات غير متساوية في أزمنة متساوية (أو العكس) يقال إنه يتحرك بسرعة غير منتظمة. في الحياة العملية يصعب للسيارات السير بسرعة منتظمة بسبب ظروف الطريق وإشارات المرور، لذا نلجأ لمفهوم 'السرعة المتوسطة' (ع̅) وهي المسافة الكلية مقسومة على الزمن الكلي.",
                            keyPoints = listOf(
                                "الحركة في خط مستقيم واتجاه واحد هي أبسط أنواع الحركة.",
                                "العاملان الأساسيان لوصف الحركة هما: المسافة (ف) والزمن (ز).",
                                "وحدة قياس السرعة: متر/ثانية (م/ث) أو كيلومتر/ساعة (كم/س). للتحويل من كم/س إلى م/ث نضرب في 5/18.",
                                "السرعة النسبية هي سرعة جسم متحرك بالنسبة لمراقب ساكن أو متحرك."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "السرعة (ع)",
                                    meaning = "المسافة المقطوعة خلال وحدة الزمن، أو المعدل الزمني للتغير في المسافة.",
                                    note = "ع = ف ÷ ز"
                                ),
                                DefinitionItem(
                                    term = "السرعة المنتظمة",
                                    meaning = "السرعة التي يتحرك بها الجسم عندما يقطع مسافات متساوية في فترات زمنية متساوية."
                                ),
                                DefinitionItem(
                                    term = "السرعة المتوسطة",
                                    meaning = "المسافة الكلية التي يقطعها الجسم المتحرك مقسومة على الزمن الكلي المستغرق في قطع هذه المسافة."
                                ),
                                DefinitionItem(
                                    term = "السرعة النسبية",
                                    meaning = "سرعة جسم متحرك بالنسبة لمراقب ساكن أو مراقب متحرك."
                                )
                            ),
                            formulas = listOf(
                                FormulaItem(
                                    name = "قانون السرعة القياسية",
                                    formula = "ع = ف / ز",
                                    explanation = "السرعة (م/ث) = المسافة (متر) ÷ الزمن (ثانية)",
                                    unit = "م/ث أو كم/س"
                                ),
                                FormulaItem(
                                    name = "السرعة المتوسطة",
                                    formula = "ع̅ = ف_الكلية / ز_الكلي",
                                    explanation = "حاصل قسمة إجمالي المسافات على إجمالي الأزمنة",
                                    unit = "م/ث"
                                ),
                                FormulaItem(
                                    name = "تحويل الوحدات",
                                    formula = "1 كم/س = 5/18 م/ث",
                                    explanation = "للتحويل من كم/س إلى م/ث نضرب في (5 / 18)",
                                    unit = "معامل تحويل"
                                )
                            ),
                            workedExamples = listOf(
                                ExampleItem(
                                    problem = "تحركت سيارة فقطعت مسافة 360 كم خلال 4 ساعات. احسب سرعتها بوحدة (كم/س) ثم بوحدة (م/ث).",
                                    steps = listOf(
                                        "المعطيات: المسافة ف = 360 كم، الزمن ز = 4 ساعات",
                                        "1) حساب السرعة بـ كم/س: ع = ف ÷ ز = 360 ÷ 4 = 90 كم/س",
                                        "2) التحويل إلى م/ث: ع = 90 × (5 ÷ 18) = 25 م/ث"
                                    ),
                                    finalAnswer = "السرعة = 90 كم/س = 25 م/ث"
                                )
                            ),
                            practiceQuestions = listOf(
                                ExerciseQuestion(
                                    question = "علل: يصعب عملياً تحرك سيارة بسرعة منتظمة داخل المدن؟",
                                    answer = "بسبب تغير أحوال الطريق وإشارات المرور وكثافة السيارات.",
                                    tip = "تذكر أن السرعة العملية تخضع دائماً لظروف الطريق"
                                ),
                                ExerciseQuestion(
                                    question = "متى تكون السرعة النسبية لجسم متحرك تساوي صفراً؟",
                                    answer = "عندما يكون المراقب يتحرك في نفس الاتجاه وبنفس السرعة.",
                                    tip = "السرعة النسبية = سرعة الجسم - سرعة المراقب"
                                )
                            ),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "q1",
                                    questionText = "حاصل ضرب السرعة في الزمن يساوي:",
                                    type = QuestionType.MCQ,
                                    options = listOf("العجلة", "المسافة", "الكثافة", "القوة"),
                                    correctOptionIndex = 1,
                                    explanation = "من المثلث الرياضي: المسافة (ف) = السرعة (ع) × الزمن (ز)"
                                ),
                                QuizQuestion(
                                    id = "q2",
                                    questionText = "السرعة النسبية لجسم يتحرك في عكس اتجاه المراقب تكون أكبر من سرعته الفعلية.",
                                    type = QuestionType.TRUE_FALSE,
                                    options = listOf("صح", "خطأ"),
                                    correctOptionIndex = 0,
                                    explanation = "صحيح، لأن السرعة النسبية في عكس الاتجاه = سرعة الجسم + سرعة المراقب"
                                ),
                                QuizQuestion(
                                    id = "q3",
                                    questionText = "سيارة تسير بسرعة 72 كم/س، فإن سرعتها تساوي ..... م/ث.",
                                    type = QuestionType.COMPLETE,
                                    correctAnswerText = "20",
                                    explanation = "72 × (5/18) = 20 م/ث"
                                )
                            ),
                            homework = "حل تمارين كتاب الوزارة ص 12 إلى 15 كاملة، ومسألة القطارين المتعاكسين."
                        ),
                        Lesson(
                            id = "sci_l2",
                            unitId = "sci_u1",
                            subjectId = "science",
                            term = Term.TERM_1,
                            title = "الدرس الثاني: التمثيل البياني للحركة والعجلة",
                            order = 2,
                            simpleSummary = "التمثيل البياني يسهل وصف الظواهر الفيزيائية. علاقة (مسافة - زمن) للسرعة المنتظمة خط مستقيم يمر بنقطة الأصل، وعلاقة (سرعة - زمن) خط مستقيم موازٍ لمحور الزمن. والعجلة هي معدل تغير السرعة في الثانية.",
                            detailedExplanation = "عندما تتغير سرعة الجسم بمرور الزمن يقال إنه يتحرك بعجلة. إذا زادت سرعته بمرور الوقت فالعجلة موجبة (تسارعية)، وإذا نقصت فالعجلة سالبة (تناقصية). وإذا تحرك الجسم بسرعة منتظمة فإن العجلة تساوي صفراً لأن التغير في السرعة Δع = 0.",
                            keyPoints = listOf(
                                "العجلة (جـ) = (ع₂ - ع₁) ÷ ز",
                                "العجلة المنتظمة الموجبة: السرعة النهائية أكبر من الابتدائية.",
                                "العجلة المنتظمة السالبة: السرعة النهائية أقل من الابتدائية.",
                                "الجسم المتحرك بسرعة منتظمة عجلته = صفر دائماً."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "العجلة (جـ)",
                                    meaning = "المعدل الزمني للتغير في السرعة، أو مقدار التغير في السرعة خلال وحدة الزمن.",
                                    note = "وحدتها م/ث²"
                                ),
                                DefinitionItem(
                                    term = "العجلة المنتظمة",
                                    meaning = "العجلة التي يتحرك بها الجسم عندما تتغير سرعته بمقادير متساوية في أزمنة متساوية."
                                )
                            ),
                            formulas = listOf(
                                FormulaItem(
                                    name = "قانون العجلة",
                                    formula = "جـ = (ع₂ - ع₁) / ز",
                                    explanation = "العجلة = (السرعة النهائية - السرعة الابتدائية) ÷ الزمن",
                                    unit = "م/ث²"
                                )
                            ),
                            workedExamples = listOf(
                                ExampleItem(
                                    problem = "تحركت سيارة من السكون فوصلت سرعتها إلى 30 م/ث خلال 6 ثوانٍ. احسب العجلة ونوعها.",
                                    steps = listOf(
                                        "تحركت من السكون تعني: ع₁ = 0",
                                        "السرعة النهائية ع₂ = 30 م/ث، الزمن ز = 6 ثوانٍ",
                                        "جـ = (30 - 0) ÷ 6 = 5 م/ث²",
                                        "النوع: عجلة منتظمة موجبة لأن السرعة تزداد."
                                    ),
                                    finalAnswer = "جـ = +5 م/ث² (عجلة موجبة)"
                                )
                            ),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "q4",
                                    questionText = "وحدة قياس العجلة هي:",
                                    type = QuestionType.MCQ,
                                    options = listOf("م/ث", "م/ث²", "كم/س", "متر"),
                                    correctOptionIndex = 1,
                                    explanation = "تقاس العجلة بمتر لكل ثانية مربعة (م/ث²)"
                                )
                            ),
                            homework = "رسم منحنى المسافة والزمن لجسم يتحرك بسرعة منتظمة وجسم ساكن."
                        )
                    )
                ),
                UnitItem(
                    id = "sci_u2",
                    subjectId = "science",
                    term = Term.TERM_1,
                    title = "الوحدة الثانية: الطاقة الضوئية (المرايا والعدسات)",
                    order = 2,
                    lessons = listOf(
                        Lesson(
                            id = "sci_l3",
                            unitId = "sci_u2",
                            subjectId = "science",
                            term = Term.TERM_1,
                            title = "الدرس الأول: المرايا المستوية والكروية",
                            order = 1,
                            simpleSummary = "المرآة المستوية تكون صورة معتدلة، مساوية للجسم، معكوسة الوضع، وتقديرية. أما المرآة المقعرة (اللامة) فتجمع الأشعة وتكون صوراً حقيقية أو تقديرية بحسب موضع الجسم بالنسبة للبؤرة ومركز التكور.",
                            detailedExplanation = "المرايا الكروية جزء من كرة جوفاء عاكسة. إذا كان السطح العاكس داخلياً فهي مقعرة، وإذا كان خارجياً فهي محدبة (مفرقة). قطب المرآة (ق) هو نقطة تتوسط السطح العاكس، ومركز التكور (م) هو مركز الكرة التي تعتبر المرآة جزءاً منها. البعد البؤري (ع) = نصف قطر التكور (نق) ÷ 2.",
                            keyPoints = listOf(
                                "نق = 2 × ع (نصف القطر يساوي ضعف البعد البؤري).",
                                "الصورة التقديرية لا يمكن استقبالها على حائل وتتكون خلف المرآة.",
                                "الصورة الحقيقية يمكن استقبالها على حائل وتكون دائماً مقلوبة.",
                                "إذا وضع جسم عند مركز التكور تكون صورته عند المركز، حقيقية مقلوبة مساوية."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "البؤرة الأصلية (ب)",
                                    meaning = "نقطة تلاقي الأشعة الضوئية المنعكسة (أو امتداداتها) عند سقوط حزمة متوازية وموازية للمحور الأصلي."
                                ),
                                DefinitionItem(
                                    term = "البعد البؤري (ع)",
                                    meaning = "المسافة بين قطب المرآة وبؤرتها الأصلية."
                                )
                            ),
                            formulas = listOf(
                                FormulaItem(
                                    name = "العلاقة بين نصف القطر والبعد البؤري",
                                    formula = "نق = 2 × ع",
                                    explanation = "نصف قطر التكور = 2 × البعد البؤري",
                                    unit = "سم"
                                )
                            ),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "q5",
                                    questionText = "مرآة مقعرة بعدها البؤري 10 سم، فإن نصف قطر تكورها يساوي:",
                                    type = QuestionType.MCQ,
                                    options = listOf("5 سم", "10 سم", "20 سم", "40 سم"),
                                    correctOptionIndex = 2,
                                    explanation = "نق = 2 × ع = 2 × 10 = 20 سم."
                                )
                            ),
                            homework = "رسم مسارات الأشعة لجسم موضوع على مسافة أكبر من ضعف البعد البؤري."
                        )
                    )
                )
            ),
            unitsTerm2 = listOf(
                UnitItem(
                    id = "sci_t2_u1",
                    subjectId = "science",
                    term = Term.TERM_2,
                    title = "الوحدة الأولى: التفاعلات الكيميائية",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "sci_t2_l1",
                            unitId = "sci_t2_u1",
                            subjectId = "science",
                            term = Term.TERM_2,
                            title = "الدرس الأول: تفاعلات الانحلال الحراري والإحلال",
                            order = 1,
                            simpleSummary = "التفاعل الكيميائي هو كسر الروابط في المواد المتفاعلة وتكوين روابط جديدة في المواد الناتجة. ينقسم إلى: تفاعلات انحلال حراري، تفاعلات إحلال (بسيط ومزدوج)، وتفاعلات أكسدة واختزال.",
                            detailedExplanation = "تنحل بعض أكاسيد المعادن بالحرارة إلى الفلز وغاز الأكسجين، مثل انحلال أكسيد الزئبق الأحمر (2HgO) إلى زئبق فضي وغاز الأكسجين. ومتسلسلة النشاط الكيميائي ترتب الفلزات تنازلياً حسب درجة نشاطها الكيميائي، حيث يحل الفلز الأكثر نشاطاً محل الفلز الأقل نشاطاً في محاليل أملاحه.",
                            keyPoints = listOf(
                                "متسلسلة النشاط الكيميائي ترتب الفلزات تنازلياً حسب النشاط الكيميائي.",
                                "ينحل هيدروكسيد النحاس الأزرق Cu(OH)2 بالحرارة إلى أكسيد نحاس أسود CuO وبخار ماء.",
                                "الأكسدة هي عملية فقد إلكترونات، والاختزال هو عملية اكتساب إلكترونات."
                            ),
                            definitions = emptyList(),
                            formulas = emptyList(),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = emptyList(),
                            homework = "حفظ متسلسلة النشاط الكيميائي ومعادلات الانحلال الحراري."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "math",
            name = "الرياضيات",
            englishName = "Mathematics",
            iconName = "Calculate",
            colorHex = 0xFF6366F1,
            description = "الجبر والإحصاء، الهندسة المستوية والتحليلية، وحساب المثلثات",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "math_u1",
                    subjectId = "math",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: العلاقات والدوال",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "math_l1",
                            unitId = "math_u1",
                            subjectId = "math",
                            term = Term.TERM_1,
                            title = "الدرس الأول: حاصل الضرب الديكارتي",
                            order = 1,
                            simpleSummary = "الزوج المرتب (أ ، ب) يختلف عن المجموعة {أ ، ب}. حاصل الضرب الديكارتي س × ص لمجموعتين س و ص هو مجموعة جميع الأزواج المرتبة التي مسقطها الأول ينتمي إلى س ومسقطها الثاني ينتمي إلى ص.",
                            detailedExplanation = "إذا كان (س ، ص) = (أ ، ب) فإن: المسقط الأول س = أ والمسقط الثاني ص = ب. عدد عناصر س × ص يساوي ن(س) × ن(ص). حاصل الضرب الديكارتي يمثل بمخطط سهمي أو مخطط بياني (ديكارتي).",
                            keyPoints = listOf(
                                "تساوي زوجين مرتبين: إذا كان (أ، ب) = (جـ، د) فإن أ = جـ و ب = د.",
                                "س × ص لا يساوي ص × س إلا إذا كانت س = ص.",
                                "ن(س × ص) = ن(س) × ن(ص)."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "الزوج المرتب (أ ، ب)",
                                    meaning = "يتكون من مسقط أول 'أ' ومسقط ثانٍ 'ب'، وترتيب العناصر فيه أساسي (أ، ب) ≠ (ب، أ)."
                                )
                            ),
                            formulas = listOf(
                                FormulaItem(
                                    name = "عدد عناصر حاصل الضرب الديكارتي",
                                    formula = "ن(س × ص) = ن(س) × ن(ص)",
                                    explanation = "عدد الأزواج المرتبة الناتجة",
                                    unit = "عنصر"
                                )
                            ),
                            workedExamples = listOf(
                                ExampleItem(
                                    problem = "إذا كان (س - 1 ، 11) = (8 ، ص + 3)، أوجد قيمة √(س + 2ص).",
                                    steps = listOf(
                                        "من تساوي الزوجين المرتبين:",
                                        "المسقط الأول: س - 1 = 8  ==> س = 8 + 1 = 9",
                                        "المسقط الثاني: ص + 3 = 11 ==> ص = 11 - 3 = 8",
                                        "المطلوب: √(س + 2ص) = √(9 + 2×8) = √(9 + 16) = √25 = 5"
                                    ),
                                    finalAnswer = "الناتج = 5"
                                )
                            ),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qm1",
                                    questionText = "إذا كان ن(س) = 3 ، ن(س × ص) = 12 ، فإن ن(ص) = .....",
                                    type = QuestionType.MCQ,
                                    options = listOf("4", "9", "36", "15"),
                                    correctOptionIndex = 0,
                                    explanation = "ن(ص) = ن(س × ص) ÷ ن(س) = 12 ÷ 3 = 4."
                                )
                            ),
                            homework = "حل تمارين ص 7 في كراسة الأنشطة."
                        )
                    )
                ),
                UnitItem(
                    id = "math_u4",
                    subjectId = "math",
                    term = Term.TERM_1,
                    title = "الوحدة الرابعة: حساب المثلثات والهندسة التحليلية",
                    order = 2,
                    lessons = listOf(
                        Lesson(
                            id = "math_l2",
                            unitId = "math_u4",
                            subjectId = "math",
                            term = Term.TERM_1,
                            title = "الدرس الأول: النسب المثلثية الأساسية للزاوية الحادة",
                            order = 1,
                            simpleSummary = "في المثلث القائم الزاوية: جيب الزاوية (جا) = المقابل ÷ الوتر، جيب تمام الزاوية (جتا) = المجاور ÷ الوتر، ظل الزاوية (ظا) = المقابل ÷ المجاور = جا ÷ جتا.",
                            detailedExplanation = "تطبق هذه النسب فقط على الزوايا الحادة في المثلث القائم. ولأي زاويتين حادتين متتامتين (مجموعهما 90 درجة) مثل أ و ب: جا أ = جتا ب ، وجتا أ = جا ب. وظا أ × ظا ب = 1. والزوايا الخاصة هي 30° و 60° و 45°.",
                            keyPoints = listOf(
                                "جا (sin) = المقابل ÷ الوتر",
                                "جتا (cos) = المجاور ÷ الوتر",
                                "ظا (tan) = المقابل ÷ المجاور = جا ÷ جتا",
                                "جا 30° = 1/2 ، جتا 60° = 1/2 ، ظا 45° = 1"
                            ),
                            definitions = emptyList(),
                            formulas = listOf(
                                FormulaItem(
                                    name = "العلاقة بين جا وجتا لزاويتين متتامتين",
                                    formula = "إذا كان قياس(أ) + قياس(ب) = 90° فإن جا أ = جتا ب",
                                    explanation = "النسب المتتامة"
                                )
                            ),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qm2",
                                    questionText = "إذا كان جا س = 1/2 حيث س زاوية حادة، فإن قياس زاوية س = .....",
                                    type = QuestionType.MCQ,
                                    options = listOf("30°", "45°", "60°", "90°"),
                                    correctOptionIndex = 0,
                                    explanation = "جا 30° = 0.5"
                                )
                            ),
                            homework = "إثبات متطابقات النسب المثلثية للزوايا 30 و 60."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        ),
        Subject(
            id = "arabic",
            name = "اللغة العربية",
            englishName = "Arabic",
            iconName = "MenuBook",
            colorHex = 0xFF10B981,
            description = "النصوص الأدبية، القراءة، قواعد النحو والصرف، وقصة طموح جارية",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "ar_u1",
                    subjectId = "arabic",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: قيم وحياة (قواعد النحو: المنادى)",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "ar_l1",
                            unitId = "ar_u1",
                            subjectId = "arabic",
                            term = Term.TERM_1,
                            title = "الدرس الأول: درس المنادى (أنواعه وإعرابه)",
                            order = 1,
                            simpleSummary = "المنادى هو اسم ظاهر يُذكر بعد أداة نداء لطلب إقباله أو تنبيهه. أدوات النداء: (يا) للقريب والبعيد، (أي، الهمزة) للقريب، (أيا، هيا) للبعيد. ينقسم المنادى إلى معرب منصوب ومبني في محل نصب.",
                            detailedExplanation = "1. المنادى المعرب المنصوب: (المضاف، الشبيه بالمضاف، النكرة غير المقصودة).\n2. المنادى المبني: يُبنى على ما يُرفع به في محل نصب ويشمل (العلم المفرد، النكرة المقصودة). عند نداء ما فيه 'أل' نأتي بـ (أيها للمذكر) و(أيتها للمؤنث) أو اسم إشارة.",
                            keyPoints = listOf(
                                "المنادى المضاف: يأتي بعده مضاف إليه ولا يُنوَّن مثل: يا طالبَ العلمِ اجتهد.",
                                "الشبيه بالمضاف: يتصل به شيء يكمل معناه وغالباً منون ومتبوع بجار ومجرور: يا طالباً للعلمِ.",
                                "النكرة المقصودة مبنية: يا طالبُ اجتهد (مبني على الضم في محل نصب).",
                                "النكرة غير المقصودة معربة منصوبة: يا طالباً اجتهد."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "المنادى",
                                    meaning = "اسم يُذكر بعد أداة من أدوات النداء لطلب استدعائه أو تنبيهه."
                                )
                            ),
                            formulas = emptyList(),
                            workedExamples = listOf(
                                ExampleItem(
                                    problem = "أعرب ما تحته خط: (يا باحثاً عن الحقِ، لا تيأس)",
                                    steps = listOf(
                                        "باحثاً: جاء منوناً وبعده شبه جملة يكمل معناه (عن الحق)، فهو منادى شبيه بالمضاف.",
                                        "الإعراب: منادى شبيه بالمضاف منصوب وعلامة نصبه الفتحة الظاهرة."
                                    ),
                                    finalAnswer = "منادى شبيه بالمضاف منصوب بالفتحة."
                                )
                            ),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qa1",
                                    questionText = "يا مصريون، اتحدوا! نوع المنادى هنا هو:",
                                    type = QuestionType.MCQ,
                                    options = listOf("مضاف", "شبيه بالمضاف", "نكرة مقصودة", "نكرة غير مقصودة"),
                                    correctOptionIndex = 2,
                                    explanation = "مبني على الواو في محل نصب لأنه نكرة مقصودة."
                                )
                            ),
                            homework = "استخراج المنادى من الآيات الكريمة المقررة وبيان نوعه وعلامة إعرابه."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        ),
        Subject(
            id = "english",
            name = "اللغة الإنجليزية",
            englishName = "English (New Hello)",
            iconName = "Language",
            colorHex = 0xFF0284C7,
            description = "New Hello for Preparatory 3: Grammar, Vocabulary, Reading, and Dialogues",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "en_u1",
                    subjectId = "english",
                    term = Term.TERM_1,
                    title = "Unit 1: Around Town",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "en_l1",
                            unitId = "en_u1",
                            subjectId = "english",
                            term = Term.TERM_1,
                            title = "Lesson 1 & 2: Places, Directions & Present Simple for Schedules",
                            order = 1,
                            simpleSummary = "In this lesson, we learn vocabulary about town places (aquarium, botanical gardens, monuments) and using the Present Simple tense to talk about official timetables (trains, buses, cinema shows).",
                            detailedExplanation = "Grammar focus: Present Simple is used for future events that are part of a fixed timetable or schedule. Examples: 'The train to Alexandria leaves at 7:00 PM.' Prepositions of time: 'at' with clock times and night, 'in' with months, seasons, and years, 'on' with days and dates.",
                            keyPoints = listOf(
                                "Use 'at' for specific times: at 5 o'clock, at the weekend.",
                                "Use 'in' for months, years, seasons: in May, in 2027, in the morning.",
                                "Use 'on' for days: on Monday, on holiday, on time.",
                                "Timetables use present simple: The library opens at 9 AM."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "Botanical gardens",
                                    meaning = "Large public gardens where plants, flowers and trees are grown for scientific study and visitor enjoyment."
                                ),
                                DefinitionItem(
                                    term = "Aquarium",
                                    meaning = "A building where people can go to see fish and other water creatures."
                                )
                            ),
                            formulas = emptyList(),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qe1",
                                    questionText = "The first metro train ..... at 6:30 AM tomorrow morning.",
                                    type = QuestionType.MCQ,
                                    options = listOf("leaves", "leaving", "is leave", "will leave"),
                                    correctOptionIndex = 0,
                                    explanation = "We use the Present Simple ('leaves') for fixed transport schedules."
                                )
                            ),
                            homework = "Write a paragraph of 110 words describing a visit to an interesting place in your town."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        ),
        Subject(
            id = "social",
            name = "الدراسات الاجتماعية",
            englishName = "Social Studies",
            iconName = "Public",
            colorHex = 0xFFD97706,
            description = "جغرافيا العالم الطبيعية والبشرية، وتاريخ مصر الحديث وبناء الدولة الوطنية",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "soc_u1",
                    subjectId = "social",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: الجغرافيا الطبيعية للعالم",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "soc_l1",
                            unitId = "soc_u1",
                            subjectId = "social",
                            term = Term.TERM_1,
                            title = "الدرس الأول: قارات العالم (الموقع والمساحة)",
                            order = 1,
                            simpleSummary = "تبلغ مساحة الكرة الأرضية 510 مليون كم²، تشغل المحيطات والبحار 70.7% واليابس 29.3%. قارات العالم القديم: آسيا وإفريقيا وأوروبا، وقارات العالم الجديد: أمريكا الشمالية، أمريكا الجنوبية، أستراليا، والقارة القطبية الجنوبية.",
                            detailedExplanation = "أكبر قارات العالم مساحة هي آسيا (44.2 مليون كم²)، وأصغرها أستراليا (7.8 مليون كم²). والمحيط الهادئ هو أكبر المحيطات، يليه الأطلنطي ثم الهندي ثم المتجمد الجنوبي وأصغرها المحيط القطبي الشمالي. الموقع الفلكي هو الموقع بالنسبة لخطوط الطول ودوائر العرض، أما الموقع الجغرافي فهو موقع المكان بالنسبة للأماكن المجاورة.",
                            keyPoints = listOf(
                                "مساحة اليابس 29.3% والماء 70.7% من مساحة الكرة الأرضية.",
                                "آسيا أكبر القارات وأستراليا أصغرها.",
                                "المحيط الهادئ أكبر المحيطات مساحة، والقطبي الشمالي أصغرها.",
                                "تفصل قارة آسيا عن أوروبا جبال أورال، وتتصل بإفريقيا عند شبه جزيرة سيناء."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "الموقع الجغرافي",
                                    meaning = "موقع المكان بالنسبة للأماكن الأخرى المجاورة من بحار ومحيطات وقارات ودول."
                                ),
                                DefinitionItem(
                                    term = "الدول الجزرية",
                                    meaning = "الدول التي تتكون من جزيرة أو أكثر، أو قد تكون جزءاً من جزيرة مثل اليابان وإندونيسيا."
                                )
                            ),
                            formulas = emptyList(),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qs1",
                                    questionText = "تعتبر قارة ..... أكبر قارات العالم مساحة.",
                                    type = QuestionType.MCQ,
                                    options = listOf("إفريقيا", "آسيا", "أمريكا الشمالية", "أوروبا"),
                                    correctOptionIndex = 1,
                                    explanation = "قارة آسيا بمساحة 44.2 مليون كم²."
                                )
                            ),
                            homework = "رسم خريطة صماء لقارات العالم وتحديد خط الاستواء ومدار السرطان والجدي."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        ),
        Subject(
            id = "religion",
            name = "التربية الدينية الإسلامية",
            englishName = "Islamic Education",
            iconName = "Mosque",
            colorHex = 0xFF0D9488,
            description = "القرآن الكريم والتجويد، العقيدة والتوحيد، العبادات والمعاملات، والشخصيات الإسلامية",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "rel_u1",
                    subjectId = "religion",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: من دلائل قدرة الله وعظمته",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "rel_l1",
                            unitId = "rel_u1",
                            subjectId = "religion",
                            term = Term.TERM_1,
                            title = "الدرس الأول: سورة المؤمنون وأحكام القلقلة",
                            order = 1,
                            simpleSummary = "توضح الآيات صفات المؤمنين الفائزين بالفردوس الأعلى: الخشوع في الصلاة، الإعراض عن اللغو، أداء الزكاة، حفظ الفروج، ورعاية الأمانات والعهود. وتدريب على أحكام التجويد وخاصة القلقلة.",
                            detailedExplanation = "القلقلة لغة: الاضطراب والتحريك. واصطلاحاً: اضطراب في مخرج الحرف الساكن عند النطق به حتى يُسمع له نبرة قوية. حروف القلقلة خمسة مجموعة في كلمة (قطب جد). وتنقسم إلى قلقلة صغرى (الحرف في وسط الكلمة) وكبرى (الحرف متطرف وموقوف عليه).",
                            keyPoints = listOf(
                                "صفات الفلاح: الخشوع في الصلاة، أداء الزكاة، الأمانة، والوفاء بالعهد.",
                                "حروف القلقلة: ق ، ط ، ب ، ج ، د (قطب جد).",
                                "القلقلة الصغرى: إذا كان الحرف ساكناً في وسط الكلمة (يقْطع، يبْدؤ).",
                                "القلقلة الكبرى: إذا كان الحرف ساكناً متطرفاً عند الوقف (الخلَقْ، محيطْ)."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "القلقلة",
                                    meaning = "اضطراب المخرج عند النطق بالحرف الساكن بحيث يُسمع له نبرة قوية تميزه."
                                )
                            ),
                            formulas = emptyList(),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qr1",
                                    questionText = "حروف القلقلة مجموعة في عبارة:",
                                    type = QuestionType.MCQ,
                                    options = listOf("يرملون", "قطب جد", "أخي هاك علماً", "أجد قط بكت"),
                                    correctOptionIndex = 1,
                                    explanation = "حروف القلقلة الخمسة مجموعة في كلمة 'قطب جد'."
                                )
                            ),
                            homework = "تلاوة وحفظ الآيات من 1 إلى 11 من سورة المؤمنون."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        ),
        Subject(
            id = "computer",
            name = "الكمبيوتر وتكنولوجيا المعلومات",
            englishName = "Computer & ICT",
            iconName = "Computer",
            colorHex = 0xFF2563EB,
            description = "حل المشكلات، خرائط التدفق (Flowcharts)، ومقدمة لغة البرمجة Visual Basic.NET",
            unitsTerm1 = listOf(
                UnitItem(
                    id = "comp_u1",
                    subjectId = "computer",
                    term = Term.TERM_1,
                    title = "الوحدة الأولى: حل المشكلات وخرائط التدفق",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "comp_l1",
                            unitId = "comp_u1",
                            subjectId = "computer",
                            term = Term.TERM_1,
                            title = "الدرس الأول: مراحل حل المشكلة ورموز خريطة التدفق",
                            order = 1,
                            simpleSummary = "المشكلة (Problem) هي موقف يتطلب إيجاد حل له وفق خطوات محددة. وتمر مراحل حل المشكلة بخمس خطوات: تحديد المشكلة، إعداد خطوات الحل الخوارزمية (Algorithm)، تصميم البرنامج، اختبار صحة البرنامج، وتوثيق البرنامج.",
                            detailedExplanation = "خريطة التدفق (Flowchart) هي تمثيل تخطيطي يعتمد على الرسم بأشكال قياسية لتوضيح ترتيب العمليات لحل مشكلة ما. الرموز الأساسية:\n- الشكل البيضاوي (Oval): للبداية والنهاية (Start / End).\n- متوازي الأضلاع (Parallelogram): للإدخال والإخراج (Input / Output).\n- المستطيل (Rectangle): للمعالجة والعمليات الحسابية (Process).\n- المعين (Rhombus): لاتخاذ القرار والمقارنة (Decision).\n- الأسهم (Flow lines): لخطوط اتجاه التدفق.",
                            keyPoints = listOf(
                                "مراحل حل المشكلة: التحديد -> الخوارزمية -> التصميم -> الاختبار -> التوثيق.",
                                "البيضاوي: بداية ونهاية.",
                                "متوازي الأضلاع: قراءة، إدخال، طباعة (Read / Input / Print).",
                                "المستطيل: العمليات الحسابية والمعادلات.",
                                "المعين: اتخاذ القرار (If/Then)، وله مخرجان على الأقل (Yes / No)."
                            ),
                            definitions = listOf(
                                DefinitionItem(
                                    term = "الخوارزمية (Algorithm)",
                                    meaning = "مجموعة من الخطوات المتسلسلة والمترابطة منطقياً لحل مشكلة معينة، منسوبة للعالم محمد بن موسى الخوارزمي."
                                ),
                                DefinitionItem(
                                    term = "خريطة التدفق (Flowchart)",
                                    meaning = "تمثيل تخطيطي يعتمد على الرسم بأشكال قياسية لتوضيح ترتيب العمليات اللازمة لحل مسألة محددة."
                                )
                            ),
                            formulas = emptyList(),
                            workedExamples = emptyList(),
                            practiceQuestions = emptyList(),
                            quizQuestions = listOf(
                                QuizQuestion(
                                    id = "qc1",
                                    questionText = "يُستخدم الشكل المعين في خرائط التدفق لـ:",
                                    type = QuestionType.MCQ,
                                    options = listOf("البداية والنهاية", "الإدخال والإخراج", "اتخاذ القرار", "المعالجة"),
                                    correctOptionIndex = 2,
                                    explanation = "المعين يمثل شرطاً أو قراراً منطقياً (Decision) يتفرع منه خطوط تدفق."
                                )
                            ),
                            homework = "رسم خريطة تدفق لجمع عددين وإظهار الناتج."
                        )
                    )
                )
            ),
            unitsTerm2 = emptyList()
        )
    )

    // Interactive Science Simulations for Grade 3 Preparatory
    val scienceExperiments: List<ScienceExperiment> = listOf(
        ScienceExperiment(
            id = "exp_speed",
            title = "محاكاة السرعة والحركة المنتظمة (v = d / t)",
            target = "استنتاج العلاقة الرياضية بين المسافة والزمن والسرعة ورسم منحنى الحركة الحية",
            tools = listOf("سيارة متحركة في مسار مستقيم", "عداد مسافة رقمي (أمتار)", "ساعة إيقاف دقيقة (ثوانٍ)", "منحنى رسم بياني حي"),
            steps = listOf(
                "حدد قيمة المسافة (d) المراد قطعها بالأمتار.",
                "حدد قيمة الزمن المستغرق (t) بالثواني.",
                "اضغط زر 'تشغيل المحاكاة' لمشاهدة حركة السيارة وتسجيل السرعة v = d / t.",
                "لاحظ ميل الخط المستقيم في الرسم البياني للسرعة المنتظمة."
            ),
            observation = "تزداد سرعة السيارة كلما زادت المسافة المقطوعة في نفس الزمن، أو كلما قل الزمن لنفس المسافة.",
            scientificExplanation = "السرعة المنتظمة تُمثل بيانياً في علاقة (مسافة - زمن) بخط مستقيم مائل يمر بنقطة الأصل لأن المسافة تتناسب طردياً مع الزمن عند ثبوت السرعة.",
            questions = listOf(
                QuizQuestion(
                    id = "q_exp1",
                    questionText = "إذا قطعت السيارة مسافة 100 متر في 5 ثوانٍ، فإن سرعتها تساوي:",
                    type = QuestionType.MCQ,
                    options = listOf("10 م/ث", "20 م/ث", "25 م/ث", "50 م/ث"),
                    correctOptionIndex = 1,
                    explanation = "ع = ف ÷ ز = 100 ÷ 5 = 20 م/ث"
                )
            ),
            type = ExperimentType.SPEED_MOTION
        ),
        ScienceExperiment(
            id = "exp_optics",
            title = "محاكاة المرآة المقعرة وتتبع مسار الأشعة الضوئية",
            target = "تحديد موضع وخواص الصورة المتكونة بالمرآة المقعرة بتغيير موضع الجسم",
            tools = listOf("مرآة مقعرة", "محور أصلي وبؤرة (ب) ومركز تكور (م)", "شمعة مضيئة قابلة للسحب والتحريك", "حائل لاستقبال الصورة"),
            steps = listOf(
                "حرك موضع الجسم (الشمعة) يساراً ويميناً على المحور الأصلي.",
                "اختر وضعاً: (أكبر من ضعف البعد البؤري)، (عند مركز التكور)، (بين البؤرة والمركز)، أو (قبل البؤرة).",
                "تتبع مسار الشعاع الساقط موازياً للمحور والمنعكس ماراً بالبؤرة.",
                "اقرأ خواص الصورة المتكونة وموضعها تلقائياً."
            ),
            observation = "تتغير خواص الصورة وحجمها وفقاً لموضع الجسم وبعده عن قطب المرآة.",
            scientificExplanation = "عندما يكون الجسم عند مركز التكور تكون صورته عند مركز التكور وتكون حقيقية مقلوبة مساوية للجسم. وإذا كان الجسم قبل البؤرة تكون صورته خلف المرآة تقديرية معتدلة ومكبرة.",
            questions = listOf(
                QuizQuestion(
                    id = "q_exp2",
                    questionText = "عند وضع جسم بين البؤرة ومركز التكور لمرآة مقعرة، تتكون له صورة:",
                    type = QuestionType.MCQ,
                    options = listOf("تقديرية مصغرة", "حقيقية مقلوبة مكبرة على بعد أكبر من ضعف البعد البؤري", "حقيقية مساوية", "لا تتكون له صورة"),
                    correctOptionIndex = 1,
                    explanation = "تتكون صورة حقيقية مقلوبة مكبرة بعد مركز التكور."
                )
            ),
            type = ExperimentType.OPTICS_MIRRORS
        ),
        ScienceExperiment(
            id = "exp_ohms",
            title = "محاكاة الدائرة الكهربائية وقانون أوم (I = V / R)",
            target = "تحقيق قانون أوم بيانياً وتوضيح العلاقة بين شدة التيار وفرق الجهد والمقاومة",
            tools = listOf("بطارية متغيرة الجهد (فولتميتر)", "مقاومة متغيرة (ريوستات)", "أميتر لقياس شدة التيار", "مصباح كهربائي متوهج"),
            steps = listOf(
                "غير قيمة فرق الجهد (V) بالفولت وشاهد قراءة الفولتميتر.",
                "غير قيمة المقاومة (R) بالأوم وشاهد قراءة الأميتر.",
                "لاحظ شدة توهج المصباح وحرارة السلك المحسوبة تلقائياً.",
                "استنتج أن شدة التيار تتناسب طردياً مع فرق الجهد وعكسياً مع المقاومة."
            ),
            observation = "تزداد شدة التيار كلما زاد الجهد، وتقل شدة التيار بزيادة المقاومة، مما يغير توهج المصباح.",
            scientificExplanation = "قانون أوم: تتناسب شدة التيار المار في موصل طردياً مع فرق الجهد بين طرفيه عند ثبوت درجة الحرارة (جـ = ت × م).",
            questions = listOf(
                QuizQuestion(
                    id = "q_exp3",
                    questionText = "إذا زاد فرق الجهد بين طرفي مقاومة ثابتة إلى الضعف، فإن شدة التيار:",
                    type = QuestionType.MCQ,
                    options = listOf("تقل للنصف", "تزداد للضعف", "تظل ثابتة", "تصل للصفر"),
                    correctOptionIndex = 1,
                    explanation = "وفق قانون أوم، التناسب طردي بين شدة التيار وفرق الجهد."
                )
            ),
            type = ExperimentType.OHMS_LAW
        ),
        ScienceExperiment(
            id = "exp_mendel",
            title = "محاكاة الوراثة وتجارب مندل لصفات نبات البسلة",
            target = "دراسة قانون مندل الأول (انعزال العوامل) وتوزيع الصفات السائدة والمتنحية في الجيلين الأول والثاني",
            tools = listOf("جينات سائدة R (قرمزي)", "جينات متنحية r (أبيض)", "مخطط مربع بانيت (Punnett Square)", "حاسبة النسب الوراثية"),
            steps = listOf(
                "اختر جينات الأب (مثال: RR نقي أو Rr هجين).",
                "اختر جينات الأم (مثال: rr أبيض متنحي).",
                "اضغط 'تلقيح وراثي' لمشاهدة توزيع الأمشاج في جدول بانيت.",
                "شاهد نسبة الأبناء والصفات الظاهرية (قرمزي : أبيض)."
            ),
            observation = "تظهر الصفة السائدة بنسبة 100% في الجيل الأول، وبنسبة 3 سائد : 1 متنحٍ في الجيل الثاني.",
            scientificExplanation = "عند تزاوج فردين نقيين يحملان صفتين متضادتين، تورث صفة أحد الأبوين فقط في الجيل الأول (الصفة السائدة)، ثم تورث الصفتان معاً في الجيل الثاني بنسبة 3 : 1.",
            questions = listOf(
                QuizQuestion(
                    id = "q_exp4",
                    questionText = "نسبة ظهور الصفة السائدة إلى المتنحية في الجيل الثاني لقانون مندل الأول هي:",
                    type = QuestionType.MCQ,
                    options = listOf("1 : 1", "2 : 1", "3 : 1", "4 : 0"),
                    correctOptionIndex = 2,
                    explanation = "تظهر بنسبة 3 سائد إلى 1 متنحٍ (75% إلى 25%)."
                )
            ),
            type = ExperimentType.MENDEL_GENETICS
        )
    )

    // Digital Bookshelf Data
    val digitalBooks: List<StudyBook> = listOf(
        StudyBook(
            id = "bk_sci_1",
            title = "كتاب العلوم - الصف الثالث الإعدادي (الفصل الدراسي الأول)",
            subjectId = "science",
            subjectName = "العلوم",
            term = Term.TERM_1,
            pageCount = 120,
            author = "وزارة التربية والتعليم المصرية 2026/2027",
            chapters = listOf(
                "الوحدة الأولى: القوى والحركة",
                "الوحدة الثانية: الطاقة الضوئية والمرايا",
                "الوحدة الثالثة: الكون والنظام الشمسي",
                "الوحدة الرابعة: التكاثر واستمرار النوع"
            ),
            pages = listOf(
                BookPage(
                    pageNumber = 1,
                    chapterTitle = "الوحدة الأولى: القوى والحركة",
                    content = "بسم الله الرحمن الرحيم. الوحدة الأولى تتناول الحركة في اتجاه واحد، السرعة القياسية والسرعة المتجهة، والعجلة المنتظمة. تعتبر دراسة الحركة أساس علم الميكانيكا الكلاسيكية التي تفسر سير المركبات وإطلاق الصواريخ وحركة الأجرام السماوية.",
                    keyTakeaway = "الحركة هي التغير النسبي لموضع الجسم عبر الزمن."
                ),
                BookPage(
                    pageNumber = 2,
                    chapterTitle = "الوحدة الأولى: القوى والحركة",
                    content = "السرعة: تعرف بأنها المسافة المقطوعة خلال وحدة الزمن. إذا قطعت سيارة 100 كيلومتر في ساعتين فإن سرعتها المتوسطة = 50 كم/س. وفي النظام الدولي تقاس السرعة بوحدة متر/ثانية (م/ث). للتحويل بين كم/س و م/ث يتم الضرب في المعامل 5/18.",
                    keyTakeaway = "ع = ف / ز (وحدة القياس م/ث أو كم/س)."
                ),
                BookPage(
                    pageNumber = 3,
                    chapterTitle = "الوحدة الأولى: القوى والحركة",
                    content = "التمثيل البياني للحركة: يمثل المحور الرأسي المسافة والمحور الأفقي الزمن في علاقة السرعة المنتظمة، حيث يظهر خط مستقيم مائل يمر بنقطة الأصل. أما إذا مثلنا السرعة على المحور الرأسي والزمن على المحور الأفقي، فيظهر خط أفقي موازٍ لمحور الزمن.",
                    keyTakeaway = "ميل منحنى (مسافة - زمن) يعبر عن قيمة السرعة."
                ),
                BookPage(
                    pageNumber = 4,
                    chapterTitle = "الوحدة الثانية: الطاقة الضوئية",
                    content = "المرايا والعدسات: ينعكس الضوء وفق قانونين أساسيين: القانون الأول (زاوية السقوط = زاوية الانعكاس)، والقانون الثاني (الشعاع الساقط والشعاع المنعكس والعمود المقام تقع جميعها في مستوى واحد عمودي على السطح العاكس).",
                    keyTakeaway = "زاوية السقوط تساوي زاوية الانعكاس دائماً."
                )
            )
        ),
        StudyBook(
            id = "bk_math_1",
            title = "كتاب الرياضيات (الجبر والهندسة وحساب المثلثات)",
            subjectId = "math",
            subjectName = "الرياضيات",
            term = Term.TERM_1,
            pageCount = 145,
            author = "توجيه عام الرياضيات - مصر",
            chapters = listOf(
                "الوحدة الأولى: العلاقات والدوال",
                "الوحدة الثانية: النسبة والتناسب والتغير",
                "الوحدة الثالثة: الإحصاء والمدى والانحراف المعياري",
                "الوحدة الرابعة: حساب المثلثات والهندسة التحليلية"
            ),
            pages = listOf(
                BookPage(
                    pageNumber = 1,
                    chapterTitle = "الوحدة الأولى: حاصل الضرب الديكارتي",
                    content = "الزوج المرتب: يتحدد الزوج المرتب بعنصرين الأول يسمى المسقط الأول والثاني يسمى المسقط الثاني. تساوي زوجين مرتبين: إذا كان (أ ، ب) = (جـ ، د) فإن أ = جـ و ب = د دائماً. مثال: إذا كان (س+1 ، 5) = (3 ، ص-2) فإن س = 2 و ص = 7.",
                    keyTakeaway = "الزوج المرتب يراعي الترتيب بدقة عكس المجموعات."
                ),
                BookPage(
                    pageNumber = 2,
                    chapterTitle = "الوحدة الرابعة: الهندسة التحليلية",
                    content = "البعد بين نقطتين: إذا كانت النقطة م(س₁ ، ص₁) والنقطة ن(س₂ ، ص₂) فإن البعد بينهما = الجذر التربيعي لـ [(س₂ - س₁)² + (ص₂ - ص₁)²]. هذا القانون مشتق مباشرة من نظرية فيثاغورس في المثلث القائم.",
                    keyTakeaway = "البعد = جذر مربع فرق السينات + مربع فرق الصادات."
                )
            )
        ),
        StudyBook(
            id = "bk_ar_1",
            title = "كتاب لغتي الجميلة وقواعد النحو - الصف الثالث الإعدادي",
            subjectId = "arabic",
            subjectName = "اللغة العربية",
            term = Term.TERM_1,
            pageCount = 110,
            author = "نخبة خبراء اللغة العربية",
            chapters = listOf(
                "النحو: المنادى، البدل، أسلوب المدح والذم، الممنوع من الصرف",
                "النصوص: عباد الرحمن، رحمة ومحبة، كن جميلاً",
                "القصة: طموح جارية (شجرة الدر)"
            ),
            pages = listOf(
                BookPage(
                    pageNumber = 1,
                    chapterTitle = "النحو: أسلوب النداء",
                    content = "المنادى هو اسم ظاهر يُذكر بعد حرف النداء طلباً لإقبال مدلوله. وحروف النداء هي: يا (لكل منادى)، أيا وهيا (للبعيد)، والهمزة وأي (للقريب). إعراب المنادى إما منصوب لفظاً أو مبني في محل نصب.",
                    keyTakeaway = "المنادى خمسة أنواع: مضاف، شبيه بالمضاف، نكرة غير مقصودة، نكرة مقصودة، علم مفرد."
                )
            )
        )
    )

    // Question Bank with authentic questions
    val questionBank: List<BankQuestion> = listOf(
        BankQuestion(
            id = "bq1",
            subjectId = "science",
            subjectName = "العلوم",
            term = Term.TERM_1,
            unitTitle = "القوى والحركة",
            lessonTitle = "الحركة في اتجاه واحد",
            type = QuestionType.MCQ,
            difficulty = Difficulty.MEDIUM,
            question = "قطار بدأ رحلته الساعة 7:00 صباحاً فقطع مسافة 300 كم بسرعة 100 كم/س، متى يصل القطار لمحطته؟",
            options = listOf("الساعة 9:00 صباحاً", "الساعة 10:00 صباحاً", "الساعة 11:00 صباحاً", "الساعة 12:00 ظهراً"),
            correctIndex = 1,
            answer = "الساعة 10:00 صباحاً",
            explanation = "الزمن المستغرق = المسافة ÷ السرعة = 300 ÷ 100 = 3 ساعات. وقت الوصول = 7:00 + 3 = 10:00 صباحاً."
        ),
        BankQuestion(
            id = "bq2",
            subjectId = "math",
            subjectName = "الرياضيات",
            term = Term.TERM_1,
            unitTitle = "الهندسة التحليلية",
            lessonTitle = "البعد بين نقطتين",
            type = QuestionType.MCQ,
            difficulty = Difficulty.EASY,
            question = "البعد بين النقطة (3 ، 4) ونقطة الأصل (0 ، 0) يساوي:",
            options = listOf("3 وحدات", "4 وحدات", "5 وحدات طول", "7 وحدات طول"),
            correctIndex = 2,
            answer = "5 وحدات طول",
            explanation = "البعد = √(3² + 4²) = √(9 + 16) = √25 = 5 وحدات طول."
        ),
        BankQuestion(
            id = "bq3",
            subjectId = "arabic",
            subjectName = "اللغة العربية",
            term = Term.TERM_1,
            unitTitle = "قواعد النحو",
            lessonTitle = "المنادى",
            type = QuestionType.MCQ,
            difficulty = Difficulty.HARD,
            question = "يا بني أقم الصلاة! نوع المنادى في جملة 'يا بني' هو:",
            options = listOf("علم مفرد", "نكرة مقصودة", "منادى مضاف", "شبيه بالمضاف"),
            correctIndex = 2,
            answer = "منادى مضاف",
            explanation = "'بني' أصلها ابن مضافاً إلى ياء المتكلم، لذلك فهو منادى مضاف منصوب بفتحة مقدرة."
        ),
        BankQuestion(
            id = "bq4",
            subjectId = "science",
            subjectName = "العلوم",
            term = Term.TERM_1,
            unitTitle = "الطاقة الضوئية",
            lessonTitle = "المرايا والعدسات",
            type = QuestionType.TRUE_FALSE,
            difficulty = Difficulty.EASY,
            question = "البعد البؤري لمرآة مقعرة نصف قطر تكورها 20 سم يساوي 10 سم.",
            options = listOf("صح", "خطأ"),
            correctIndex = 0,
            answer = "صح",
            explanation = "البعد البؤري ع = نصف القطر ÷ 2 = 20 ÷ 2 = 10 سم."
        ),
        BankQuestion(
            id = "bq5",
            subjectId = "english",
            subjectName = "اللغة الإنجليزية",
            term = Term.TERM_1,
            unitTitle = "Around Town",
            lessonTitle = "Present Simple & Timetables",
            type = QuestionType.MCQ,
            difficulty = Difficulty.MEDIUM,
            question = "The plane to Cairo ..... at 8:15 tonight.",
            options = listOf("land", "lands", "is landing", "will land"),
            correctIndex = 1,
            answer = "lands",
            explanation = "Present Simple ('lands') expresses scheduled timetable events."
        ),
        BankQuestion(
            id = "bq6",
            subjectId = "social",
            subjectName = "الدراسات الاجتماعية",
            term = Term.TERM_1,
            unitTitle = "جغرافيا العالم",
            lessonTitle = "قارات العالم",
            type = QuestionType.MCQ,
            difficulty = Difficulty.EASY,
            question = "أصغر قارات العالم من حيث المساحة هي قارة:",
            options = listOf("أوروبا", "أمريكا الجنوبية", "أستراليا", "إفريقيا"),
            correctIndex = 2,
            answer = "أستراليا",
            explanation = "أستراليا هي أصغر القارات مساحة وتبلغ 7.8 مليون كم²."
        ),
        BankQuestion(
            id = "bq7",
            subjectId = "computer",
            subjectName = "الكمبيوتر",
            term = Term.TERM_1,
            unitTitle = "حل المشكلات",
            lessonTitle = "خرائط التدفق",
            type = QuestionType.MCQ,
            difficulty = Difficulty.EASY,
            question = "الشكل المستخدم لإدخال أو إخراج البيانات في خريطة التدفق هو:",
            options = listOf("المستطيل", "المعين", "متوازي الأضلاع", "الشكل البيضاوي"),
            correctIndex = 2,
            answer = "متوازي الأضلاع",
            explanation = "يستخدم متوازي الأضلاع لكتابة أوامر الإدخال مثل Input أو القراءة Read والطباعة Print."
        )
    )

    // Final Revision summaries for each subject
    val revisionSummaries: List<SubjectRevisionSummary> = listOf(
        SubjectRevisionSummary(
            subjectId = "science",
            subjectName = "العلوم",
            term = Term.TERM_1,
            capsuleSummary = "كبسولة امتحان العلوم 3 إعدادي: السرعة ع = ف ÷ ز، العجلة جـ = (ع₂ - ع₁) ÷ ز، المرايا الكروية نق = 2ع، الصورة التقديرية دائماً معتدلة، الحقيقية دائماً مقلوبة. الانقسام الميتوزي ينتج عنه خليتان بكل منهما 2ن، والميوزي 4 خلايا بكل منها ن لإنتاج الأمشاج.",
            goldenRules = listOf(
                "إذا بدأ الجسم حركته من السكون فإن ع₁ = 0.",
                "إذا توقف الجسم أو ضغط السائق على الفرامل فإن ع₂ = 0.",
                "السرعة النسبية في نفس الاتجاه = الفرق بين السرعتين، وفي عكس الاتجاه = المجموع.",
                "الجسم عند مركز التكور صورته عند المركز حقيقية مقلوبة مساوية."
            ),
            commonMistakes = listOf(
                "نسيان تحويل الوحدات من كم/س إلى م/ث بضربها في 5/18.",
                "الخلط بين الصورة الحقيقية (تستقبل على حائل ومقلوبة) والتقديرية (لا تستقبل ومعتدلة).",
                "كتابة العجلة سالبة دون وضع إشارة السالب في الناتج النهائي."
            ),
            nightBeforeExamTips = listOf(
                "احرص على استخدام المسطرة وقلم الرصاص في رسم مسارات الأشعة الضوئية.",
                "اكتب القانون الفيزيائي أولاً قبل التعويض بالأرقام لضمان درجات القانون.",
                "راجع أسئلة 'ما معنى قولنا أن' وأسئلة 'علل لما يأتي' بعناية."
            )
        ),
        SubjectRevisionSummary(
            subjectId = "math",
            subjectName = "الرياضيات",
            term = Term.TERM_1,
            capsuleSummary = "كبسولة امتحان الرياضيات: حاصل الضرب الديكارتي س × ص لا يساوي ص × س. الدالة: كل عنصر من س يخرج منه سهم واحد فقط. الميل م = فرق الصادات ÷ فرق السينات. المستقيمان المتوازيان ميليهما متساويان (م₁ = م₂)، والمتعامدان م₁ × م₂ = -1. معادلة الخط المستقيم: ص = م س + جـ.",
            goldenRules = listOf(
                "جا² س + جتا² س = 1 لأي زاوية حادة.",
                "ميل المستقيم الموازي لمحور السينات = 0، والموازي لمحور الصادات غير معرف.",
                "إحداثيا منتصف قطعة مستقيمة = ((س₁ + س₂) / 2 ، (ص₁ + ص₂) / 2)."
            ),
            commonMistakes = listOf(
                "نسيان وضع ± عند أخذ الجذر التربيعي للطرفين في المعادلات.",
                "عكس الترتيب في قانون الميل: الترتيب الصحيح هو (ص₂ - ص₁) في البسط و (س₂ - س₁) في المقام."
            ),
            nightBeforeExamTips = listOf(
                "تأكد من إحضار أدواتك الهندسية والآلة الحاسبة.",
                "تحقق دائماً من شروط الدالة في أسئلة العلاقات والبيان."
            )
        )
    )

    // Predefined Achievements
    val initialAchievements: List<Achievement> = listOf(
        Achievement(
            id = "ach_first_lesson",
            title = "أول درس مكتمل",
            description = "أكملت قراءة ومذاكرة أول درس في المنهج بنجاح",
            iconEmoji = "🏆",
            isUnlocked = true,
            progress = 1,
            maxProgress = 1
        ),
        Achievement(
            id = "ach_streak_7",
            title = "7 أيام مذاكرة مستمرة",
            description = "واصلت الدخول والمذاكرة يومياً لمدة أسبوع كامل",
            iconEmoji = "🔥",
            isUnlocked = false,
            progress = 5,
            maxProgress = 7
        ),
        Achievement(
            id = "ach_100_questions",
            title = "عبقري الأسئلة (100 سؤال)",
            description = "أجبت على 100 سؤال في بنك الأسئلة والامتحانات",
            iconEmoji = "🧠",
            isUnlocked = false,
            progress = 42,
            maxProgress = 100
        ),
        Achievement(
            id = "ach_lab_master",
            title = "عالم المختبر",
            description = "أجريت جميع تجارب المختبر التفاعلي وحققت نتائجها",
            iconEmoji = "🔬",
            isUnlocked = true,
            progress = 4,
            maxProgress = 4
        ),
        Achievement(
            id = "ach_math_hero",
            title = "متفوق الرياضيات",
            description = "حللت معادلات الدرجة الثانية وحساب المثلثات بدون أخطاء",
            iconEmoji = "⭐",
            isUnlocked = true,
            progress = 1,
            maxProgress = 1
        ),
        Achievement(
            id = "ach_active_reader",
            title = "قارئ نشيط",
            description = "قرأت 20 صفحة من المكتبة الرقمية واستخدمت البحث والعلامات",
            iconEmoji = "📚",
            isUnlocked = false,
            progress = 12,
            maxProgress = 20
        )
    )
}
