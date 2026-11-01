# Summarises privacy policies with Ollama model

import sys
from functools import lru_cache
from pathlib import Path

import ollama
from pydantic import BaseModel



POLICIES_DIR = Path(__file__).resolve().parent / "policies"

MODEL = "gemma3:12b"

CONTEXT_SIZE = 16384

QUESTIONS = {
    "data_collected": (
        "What data does this app collect?",
        "e.g. location, contacts, photos, browsing activity or personal information",
    ),

    "data_use": (
        "How does this app use your data?",
        "e.g. personalised recommendations, tracking activity, "
        "targeted advertisements or improving services",
    ),

    "data_sharing": (
        "Who is your data shared with?",
        "e.g. advertising companies, business partners, "
        "analytics providers or other organisations",
    ),

    "your_choices": (
        "Can you control or delete your data?",
        "e.g. turn off tracking, opt out of marketing, "
        "request your information or delete your data",
    ),

    "data_retention": (
        "How long does the app keep your data?",
        "e.g. is it deleted when you close your account, "
        "or can some of it remain stored afterwards?",
    ),
}


class PolicyAnswers(BaseModel):
    data_collected: list[str]
    data_use: list[str]
    data_sharing: list[str]
    your_choices: list[str]
    data_retention: list[str]


QUESTION_LINES = "\n".join(
    f"{key}: {question} ({hint})" for key, (question, hint) in QUESTIONS.items()
)

# Tells the model what kind of summary we want.
INSTRUCTIONS = (
    "You explain privacy policies to people with no technical knowledge. "
    "Read the privacy policy and answer each of these questions:\n\n"
    + QUESTION_LINES
    + "\n\nRules for every answer:\n"
    "- Give 2 to 4 points per question, never more than 4.\n"
    "- Each point is one short sentence of no more than 12 words.\n"
    "- Use simple, everyday words, as if explaining to a friend.\n"

    "- Call the company by its name from the policy, or \"the app\". "
    "Never write \"we\", \"us\" or \"our\".\n"
    "- Never use legal or technical words. Instead of these words, "
    "say what they mean:\n"
    "  affiliates, subsidiaries -> companies owned by the same group\n"
    "  third parties, service providers -> other companies\n"
    "  analytics -> studying how people use the app\n"
    "  personal information -> your details\n"
    "  retain -> keep\n"
    
    # Stops the model guessing when the policy is silent on something.
    "- Only use information from the policy. Do not guess. If the policy "
    "does not answer a question, give one point: \"The policy does not say.\""
)


def summarise_policy(policy_text):
    response = ollama.chat(
        model=MODEL,
        messages=[
            {"role": "system", "content": INSTRUCTIONS},
            {"role": "user", "content": policy_text},
        ],

        format=PolicyAnswers.model_json_schema(),
        options={"num_ctx": CONTEXT_SIZE, "temperature": 0},
        keep_alive="30m",
    )

    answers = PolicyAnswers.model_validate_json(response.message.content)

    sections = []
    for key, (question, hint) in QUESTIONS.items():
        points = getattr(answers, key)
        sections.append({
            "key": key,
            "question": question,
            "points": [clean_point(point) for point in points],
        })

    return sections



def clean_point(point):
    return point.replace("**", "").lstrip("*-• ").strip()


@lru_cache
def summarise_app_policy(package_name):

    policy_file = POLICIES_DIR / (Path(package_name).name + ".txt")

    if not policy_file.exists():
        return None

    policy_text = policy_file.read_text(encoding="utf-8")
    return summarise_policy(policy_text)



if __name__ == "__main__":
    for section in summarise_app_policy(sys.argv[1]):
        print(section["question"])
        for point in section["points"]:
            print("  •", point)
