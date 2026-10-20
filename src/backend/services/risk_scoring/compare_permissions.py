import json


def load_json(path):
    with open(path, "r", encoding="utf-8-sig") as f:
        return json.load(f)


def count_known_permissions(app_permissions, knowledge_base):
    count = 0
    for permission in app_permissions:
        if permission in knowledge_base:
            count += 1
    return count


def count_categories(app_permissions, knowledge_base):
    categories = set()
    for permission in app_permissions:
        if permission in knowledge_base:
            categories.add(knowledge_base[permission]["category"])
    return len(categories)
