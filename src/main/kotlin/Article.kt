package org.example

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("Article breakdown")
data class Article(
    @property:LLMDescription("Title of the article")
    val title: String,
    @property:LLMDescription("Article author")
    val author: String,
    @property:LLMDescription("Article publisher")
    val publisher: String,
    @property:LLMDescription("Date of the article publication in format YYYY-MM-DD:HH-MM")
    val date: String,
    @property:LLMDescription("Propositions presented in the article")
    val facts: List<Proposition>,
) {
    @Serializable
    data class Proposition(
        @property:LLMDescription("Come up with an id for this proposition")
        val id: String,
        @property:LLMDescription("The very essence of the proposition")
        val content: String,
        @property:LLMDescription("Source of information")
        val source: String,
        @property:LLMDescription("How this proposition specified by id relates to other propositions in the article")
        val relations: Map<String, String>? = null,
    )
}

val articleExamples = listOf(
    Article(
        title = "Выведенные в лаборатории ЦРУ комары-русофобы оказались неспособны отличить русских от украинцев",
        author = "Аполлинарий Ягужинский",
        publisher = "Панорама",
        date = "2025-05-06",
        facts = listOf(
            Article.Proposition(
                id = "P00001",
                content = "В США проводился секретный проект Москитная демократия.",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00002",
                content = "Цель проекта Москитная демократия - создать биологическое оружия, способное избирательно поражать граждан России, оставляя в неприкосновенности подданных других государств.",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00003",
                content = "Реализация проекта Москитная демократия осуществлялась через созданию генетически модифицированных комаров, запрограммированных на уничтожение исключительно граждан Российской Федерации.",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00004",
                content = "В ходе полевых испытаний генетически модифицированных комаров выяснилось, что они неспособны отличать русских от украинцев, белорусов и даже некоторых финнов.",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00005",
                content = "Программа Москитная демократия была окончательно свёрнута после того, как комар укусил посла Израиля в США Давида Соломоновича Бернштейна.",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00006",
                content = "Причина неудачи проекта Москитная демократия в «генетической близости» целевых групп",
                source = "Эксперты энтомологи"
            ),
            Article.Proposition(
                id = "P00007",
                content = "Проект Москитная демократия был заморожен",
                source = "Анонимный источник в ЦРУ США"
            ),
            Article.Proposition(
                id = "P00008",
                content = "Генетически модифицированные комары были выпущены на волю в штате Флорида, США",
                source = "Анонимный источник в ЦРУ США"
            ),
        ),
    ),
)

val panoramaArticle = """
# «Роскосмос» изучает возможность использования спутниковых технологий для окрашивания пасхальных яиц в отдалённых регионах

## Государственная корпорация «Роскосмос» изучает возможность применения спутниковых технологий для равномерного окрашивания пасхальных яиц в отдаленных и труднодоступных сельских районах России.

ИА Панорама  15 апр. 2025 г., 16:00 Эрвин Кляйн

Проект предполагает использование модифицированного микроволнового излучения для нанесения красителей на яйца непосредственно в хозяйствах, что, по мнению разработчиков, позволит снизить логистические издержки и обеспечить доступность традиционных пасхальных атрибутов для всех граждан.

«Мы рассматриваем различные варианты использования космических технологий для решения социально значимых задач, – заявил представитель «Роскосмоса». – Идея находится на стадии предварительной проработки. Речь идет о контролируемом воздействии микроволнового диапазона, которое позволит равномерно закрепить краситель на поверхности яйца без ущерба для его пищевых качеств».

Вместе с тем, эксперты высказывают опасения относительно безопасности и экономической целесообразности проекта.

«Необходимо провести тщательные исследования воздействия микроволнового излучения на пищевые продукты и окружающую среду», – отмечает жительница села Берёзовка, Воронежской области, Мария Загорулько. 

В то же время протоиереи считают, что яйца, окрашенные из космоса, не нужно отдельно освящать из-за близости спутников к небесной тверди.
""".trimIndent()

val apArticleVisas = """
    ASSOCIATED PRESS
    
    Visa cancellations sow panic for international students, with hundreds fearing deportation
    
    By Alice Masquelier-Page

    WASHINGTON (AP) — At first, the bar association for immigration attorneys began receiving inquiries from a couple students a day. These were foreigners studying in the U.S., and they’d discovered in early April their legal status had been terminated with little notice. To their knowledge, none of the students had committed a deportable offense.

    In recent days, the calls have begun flooding in. Hundreds of students have been calling to say they have lost legal status, seeking advice on what to do next.

    “We thought it was going to be something that was unusual,” said Matthew Maiona, a Boston-based immigration attorney who is getting about six calls a day from panicked international students. “But it seems now like it’s coming pretty fast and furious.”

    The speed and scope of the federal government’s efforts to terminate the legal status of international students have stunned colleges across the country. Few corners of higher education have been untouched, as schools ranging from prestigious private universities, large public research institutions and tiny liberal arts colleges discover status terminations one after another among their students.

    At least 790 students at more than 120 colleges and universities have had their visas revoked or their legal status terminated in recent weeks, according to an Associated Press review of university statements and correspondence with school officials. Advocacy groups collecting reports from colleges say hundreds more students could be caught up in the crackdown.

    Students apparently targeted over minor infractions
    Around 1.1 million international students were in the United States last year — a source of essential revenue for tuition-driven colleges. International students are not eligible for federal financial aid, and their ability to pay tuition often factors into whether they will be admitted to American schools. Often, they pay full price.

    Many of the students losing their legal status are from India and China, which together account for more than half the international students at American colleges. But the terminations have not been limited to those from any one part of the world, lawyers said.

    Four students from two Michigan universities are suing Trump administration officials after their F-1 student status was terminated last week. Their attorney with the American Civil Liberties Union, Ramis Wadood, said the students never received a clear reason why.

    “We don’t know, and that’s the scary part,” he said.

    The students were informed of the status terminations by their universities via email, which came as a shock, Wadood said. The reason given was that there was a “criminal records check and/or that their visa was revoked,” Wadood said, but none of them were charged or convicted of crimes. Some had either speeding or parking tickets, but one didn’t have any, he said. Only one of the students had known their entry visa was revoked, Wadood said.

    Last month, Secretary of State Marco Rubio said the State Department was revoking visas held by visitors who were acting counter to national interests, including some who protested Israel’s war in Gaza and those who face criminal charges.

    But many students say they don’t fall under those categories. Students have filed lawsuits in several states, arguing they were denied due process.

    In New Hampshire, a federal judge last week granted a temporary restraining order to restore the status of a Ph.D. student at Dartmouth College, Xiaotian Liu. On Tuesday, a federal judge in Wisconsin issued a similar order, ruling the government could not take steps to detain or revoke the visa of a University of Wisconsin-Madison gradate student.

    In a break from past, feds cancel students’ status directly
    At many colleges, officials learned the legal immigration status of some international students had been terminated when staff checked a database managed by the Department of Homeland Security. In the past, college officials say, legal statuses typically were updated after colleges told the government the students were no longer studying at the school.

    The system to track enrollment and movements of international students came under the control of Immigration and Customs Enforcement after 9/11, said Fanta Aw, CEO of NAFSA, an association of international educators. She said recent developments have left students fearful of how quickly they can be on the wrong side of enforcement.

    “You don’t need more than a small number to create fear,” Aw said. “There’s no clarity of what are the reasons and how far the reach of this is.”

    Her group says as many as 1,300 students have lost visas or had their status terminated, based on reports from colleges.

    The Department of Homeland Security and State Department did not respond to messages seeking comment.

    Foreigners who are subject to removal proceedings are usually sent a notice to appear in immigration court on a certain date, but lawyers say affected students have not received any notices, leaving them unsure of next steps to take.

    Some schools have told students to leave the country to avoid the risk of being detained or deported. But some students have appealed the terminations and stayed in the United States while those are processed.

    Still others caught in legal limbo aren’t students at all. They had remained in the U.S. post-graduation on “optional practical training,” a one-year period — or up to three for science and technology graduates — that allows employment in the U.S. after completing an academic degree. During that time, a graduate works in their field and waits to receive their H-1B or other employment visas if they wish to keep working in the U.S.

    Around 242,000 foreigners in the U.S. are employed through this “optional practical training.” About 500,000 are pursuing graduate degrees, and another 342,000 are undergraduate students.

    Among the students who have filed lawsuits is a Georgia Tech Ph.D. student who is supposed to graduate on May 5, with a job offer to join the faculty. His attorney Charles Kuck said the student was likely targeted for termination because of an unpaid traffic fine from when the student lent his car to a friend. Ultimately, the violation was dismissed.

    “We have case after case after case exactly like that, where there is no underlying crime,” said Kuck, who is representing 17 students in the federal lawsuit. He said his law firm has heard from hundreds of students.

    “These are kids who now, under the Trump administration, realize their position is fragile,” he said. “They’ve preyed on a very vulnerable population. These kids aren’t hiding. They’re in school.”

    Some international students have been adapting their daily routines.

    A Ph.D. student from China at University of North Carolina at Chapel Hill said she has begun carrying around her passport and immigration paperwork at the advice of the university’s international student office. The student, who spoke on condition of anonymity for fear of being targeted by authorities, said she has been distressed to see the terminations even for students like her without criminal records.

    “That is the most scary part because you don’t know whether you’re going to be the next person,” she said. ___

    Seminera reported from Raleigh, N.C., and Keller reported from Albuquerque, N.M.

    ___

    The Associated Press’ education coverage receives financial support from multiple private foundations. AP is solely responsible for all content. Find AP’s standards for working with philanthropies, a list of supporters and funded coverage areas at AP.org.
""".trimIndent()

val foxArticleVisas = """
    FOX NEWS
    
    International students sue over Trump admin revoking visas
    Lawsuits filed by international students against the Trump administration challenge visa revocations.

    By Rachel Wolf

    International students whose visas have been revoked are suing the Trump administration over an alleged violation of due process. 

    The Associated Press reported the students are arguing that the Trump administration lacked justification when canceling visas.

    President Donald Trump has pursued a crackdown on immigration that initially focused on those in the U.S. illegally. However, several international students have been caught in the crosshairs as the administration argues their pro-Palestinian activism constitutes support for Hamas, a U.S.-designated terrorist organization. Meanwhile, other international students have reportedly had their visas revoked for past infractions such as traffic violations, according to the AP.

    Students in at least 30 states have had their visas revoked, according to NBC News, which said the government is using a foreign policy statute from 1952 to justify the cancelations. 

    Harvard University gate
    People walk through the gate on Harvard Yard on the Harvard University campus on June 29, 2023 in Cambridge, Massachusetts. (Getty Images)

    TRUMP COLLEGE CRACKDOWN: LIST OF STUDENTS DETAINED AMID ANTISEMITISM ON CAMPUSES

    One of the most prominent cases is that of Mahmoud Khalil, a Columbia University student who was involved in anti-Israel activity at the school. Last week, a judge ruled that the government met the burden of proof to deport Khalil. Attorneys for the Department of Homeland Security (DHS) said Khalil was not forthcoming about his involvement with certain organizations or his work with the United Nations Relief and Works Agency.

    International students looking to study in the U.S. must meet several requirements to obtain a specific visa that allows them to attend school. According to the State Department, the type of school and course of study the student wishes to pursue determines which visa they need.  

    Mahmoud Khalil protest
    People demonstrate outside the Thurgood Marshall United States Courthouse on the day of a hearing on the detention of Palestinian activist and Columbia University graduate student Mahmoud Khalil in New York City on March 12, 2025. (REUTERS/Shannon Stapleton)

    LAWYERS FOR COLUMBIA ANTI-ISRAEL ACTIVIST MAHMOUD KHALIL BLAST RUBIO EVIDENCE LETTER: 'TWO PAGES, THAT'S IT'

    DHS lists several circumstances under which the government may revoke a student visa, including absence from the U.S. for five months or longer, expulsion, unauthorized employment and failure to enroll, among others.

    International students have been allowed to maintain their legal residency status and continue their studies even after having their visas revoked, according to the AP. In those cases, the lack of a visa only impacted their ability to travel in and out of the U.S. According to the AP, losing legal residency status is what ultimately puts students at risk of being deported.

    Student protesters gather in protest inside their encampment on the Columbia University campus on Monday, April 29, 2024 in New York City. (AP Photo/Stefan Jeremiah)

    Trump administration officials have defended the revocation of student visas, stating that the government reserves the right to cancel them.

    "There is no right to a student visa.  We can cancel a student visa under the law just the same way that we can deny a student visa under the law. And we will do so in cases we find appropriate," Secretary of State Marco Rubio told reporters on March 28.

    Fox News Digital reached out to U.S. Citizenship and Immigration Services for comment on the lawsuits.

    Fox News Digital’s Sarah Rumpf-Whitten and Brooke Taylor contributed to this report.

    Rachel Wolf is a breaking news writer for Fox News Digital and FOX Business.
""".trimIndent()

val cnnArticleVisas = """
    CNN
    
    More than 1,000 international students and graduates in the US have had their visas revoked or statuses terminated
    In a stunning blow to international scholars who had hopes of studying in the United States, the Trump administration has revoked hundreds of student visas in nearly every corner of the country as part of a vast immigration crackdown.

    By Caroll Alvarado, Javon Huynh, Amanda Musa 

    The Trump administration has revoked hundreds of student visas in nearly every corner of the country as part of a vast immigration crackdown – and few universities know why.

    More than 1,000 international students and recent graduates at more than 130 schools in the US have had their visas or statuses revoked in the Student and Exchange Visitor Information System this year, according to university statements and spokespeople.

    Colleges and universities in 40 states have confirmed the visa and status termination of their students to CNN.

    At Middle Tennessee State University, six students from countries in Asia, Europe and the Middle East had their visas revoked, according to university spokesperson Jimmy Hart.

    “The University does not know the specific reason(s) for the visa status changes, only that they were changed within the federal database that monitors them,” Hart said.

    Several university statements said the government did not provide a reason for its actions. In most cases, universities discovered the visa revocations by checking the system. Only a handful of universities said they knew why their students’ visas or SEVIS accounts were terminated.

    “In the last few weeks, the US Department of Homeland Security has revoked the visa status of four international students at the UO based on unspecified criminal charges,” said Eric Howald, a spokesperson for the University of Oregon. “The university was not informed in advance and has not been given details about the nature of the criminal charges.”

    It is unclear if all the students whose visas have been terminated have to immediately leave the country or can stay to continue their education.

    International students – including at prestigious American universities – have been targeted amid the Trump administration’s larger immigration crackdown. Cases have ranged from high-profile cases involving alleged support of terror organizations to relatively minor offenses like years-old misdemeanors.

    Federal officials have not specified the reasons why many students’ visas are being revoked, but Secretary of State Marco Rubio has repeatedly said that some behavior, including participating in protests, will not be tolerated.

    “They’re here to go to class. They’re not here to lead activist movements that are disruptive and undermine our universities,” Rubio said.

    The University of California, Los Angeles, told CNN that 12 of its current or recent graduates were impacted by the terminations. “The termination notices indicate that all terminations were due to violations of the terms of the individuals’ visa programs,” the university’s chancellor Julio Frenk said in a statement he sent to the university community.

    Students sue the Trump administration
    While many students have not received a straightforward answer from the White House and DHS about their status removal, a new federal lawsuit against the Trump administration seeks to stop student visa revocations and reinstate those that have already been revoked.

    The lawsuit, filed at the US District Court of Northern Georgia in Atlanta, currently includes the cases of 133 foreign nationals, including students from India, China, Colombia, Mexico and Japan, according to the complaint and Dustin Baxter, one of the filing attorneys.

    The students are not identified by name in the complaint, but rather by a “pseudonym due to fear of retaliation by Defendants.” The lawsuit names three Trump administration officials as defendants: US Attorney General Pam Bondi, Homeland Security Secretary Kristi Noem, and Immigration and Customs Enforcement Acting Director Todd Lyons.

    The complaint alleges that ICE has abruptly and unlawfully terminated the students’ legal status in the United States “(…) stripping them of their ability to pursue their studies and maintain employment in the United States and risking their arrest, detention, and deportation.”

    The White House and DHS have not responded to CNN’s request for comment.

    The administration, the suit says, has terminated their status by removing the students from the Student and Exchange Visitor Information System (SEVIS) used by the Department of Homeland Security to maintain information mainly regarding international students and their status in the country.

    In late March, Rubio said that more than 300 visas, “primarily student visas, some visitor visas,” had been revoked.

    Perhaps the most high-profile deportation case of foreign nationals accused of supporting terrorist organizations involves Mahmoud Khalil, a prominent Palestinian activist and Columbia graduate who is a legal permanent US resident through a green card.

    CNN’s Rafael Romo, Arriyanna Brookins, Jillian Sykes, Julianna Bragg, Maria Aguilar Prieto, Rebekah Riess, and Yash Roy contributed to this report.
""".trimIndent()