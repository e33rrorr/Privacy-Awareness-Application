# The main responsibility of this module is to handle risk scoring related API endpoints.

from fastapi import APIRouter

from backend.schemas.app_risk import AppRisk
from backend.services.risk_scoring.score_apps import score_saved_apps


risk_router = APIRouter()



# Returns the risk level of every app saved in the apps folder.
@risk_router.get("/risk_scoring", response_model=list[AppRisk])
def get_risk_scoring():
    return score_saved_apps()
