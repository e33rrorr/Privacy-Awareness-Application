import pytest

from backend.services.risk_scoring.risk_calculator import calculate_risk


# Each row is one test case:
TEST_CASES = [
    (0, "low"),
    (2, "low"),
    (3, "medium"),
    (4, "medium"),
    (5, "high"),
    (6, "high"),
]


@pytest.mark.parametrize("sensitive_categories, expected_risk", TEST_CASES)
def test_calculate_risk(sensitive_categories, expected_risk):
    actual_risk = calculate_risk(sensitive_categories)

    assert actual_risk == expected_risk
