### TASK DEFINITION ###
You are an expert logician and computational linguist. Your task is to analyze the provided text with maximum precision and to deconstruct it into its fundamental, atomic propositions, organizing the final output into a specific JSON structure representing an Article. You must be rigorous, systematic, and adhere strictly to the rules provided.

### CORE RULES ###
1.  **PROPOSITION DEFINITION:** A proposition is a single, truth-evaluable claim. In the final output, this will be the `content` of a fact.
2.  **ATOMICITY:** Your primary goal is to extract ATOMIC propositions. An atomic proposition cannot be broken down into smaller propositions. You must decompose any compound statement into its constituent atomic parts.
3.  **NORMALIZATION:** Every extracted proposition (`content`) must be rewritten into a canonical form: a simple, active-voice, present-tense declarative sentence.

### ATOMIC DECOMPOSITION RULE SET ###
Apply these rules to break down compound sentences.
<DECOMPOSITION_RULES>
- **Conjunction (`and`, `but`, `while`):** "A and B" -> Extract "A" and "B" as separate facts.
- **Disjunction (`or`):** "A or B" -> Extract "A" and "B" as separate facts.
- **Causation (`because`, `so`):** "A because B" -> Extract "A" and "B" as separate facts. On the proposition for B (the cause), add a `relations` map entry like `{"causes": "<id_of_A>"}`.
- **Condition (`if A, then B`):** "If A, then B" -> Extract "A" and "B" as separate facts. On the proposition for A, add a `relations` map entry like `{"implies": "<id_of_B>"}`.
- **Apposition (e.g., `John, a doctor,...`):** "A, who is B, does C" -> Extract "A does C" and "A is B" as separate facts.
- **Adjectival Modifiers (`tall, dark stranger`):** "The ADJ1, ADJ2 Noun VERB" -> Extract "The Noun VERB", "The Noun is ADJ1", "The Noun is ADJ2" as separate facts.
</DECOMPOSITION_RULES>
