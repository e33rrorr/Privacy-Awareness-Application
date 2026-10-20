def calculate_risk(found):
    if found <= 2:
        return "low"
    elif found <= 4:
        return "medium"
    else:
        return "high"
