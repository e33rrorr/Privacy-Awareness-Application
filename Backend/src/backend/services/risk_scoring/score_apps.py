# Scores every saved app's permissions using the knowledge base,
# compare_permissions.py and risk_calculator.py.

from pathlib import Path

from backend.paths import APPS_DIR
from backend.services.risk_scoring.compare_permissions import load_json, count_categories
from backend.services.risk_scoring.risk_calculator import calculate_risk


# The knowledge_base folder next to this file.
KNOWLEDGE_BASE_DIR = Path(__file__).resolve().parent / "knowledge_base"


def load_knowledge_base():
    # Combines the general and health permissions into a single dictionary
    permissions = load_json(KNOWLEDGE_BASE_DIR / "permissions.json")
    health_permissions = load_json(KNOWLEDGE_BASE_DIR / "health_permissions.json")

    return {**permissions, **health_permissions}


def score_saved_apps():
    knowledge_base = load_knowledge_base()
    results = []

    # Each saved file is named after the app's package name,
    # e.g. "com.waze.json", so the name without ".json" is the package name.
    for file_path in sorted(APPS_DIR.glob("*.json")):
        app_permissions = load_json(file_path)

        categories = count_categories(app_permissions, knowledge_base)
        risk = calculate_risk(categories)

        results.append({"package_name": file_path.stem, "risk": risk})

    return results
