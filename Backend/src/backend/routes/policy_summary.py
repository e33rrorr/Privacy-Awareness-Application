from fastapi import APIRouter

from backend.schemas.policy_summary import PolicySummary
from backend.services.policy_summariser.policy_link import load_policy_link
from backend.services.policy_summariser.summarise_policy import summarise_app_policy


policy_summary_router = APIRouter()


@policy_summary_router.get("/policy_summary/{package_name}", response_model=PolicySummary)
def get_policy_summary(package_name: str):
    summary = summarise_app_policy(package_name)
    policy_url = load_policy_link(package_name)

    return {"package_name": package_name, "summary": summary, "policy_url": policy_url}


