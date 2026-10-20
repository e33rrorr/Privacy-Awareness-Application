# The main responsibility of this module is to handle risk scoring related API endpoints.

from fastapi import APIRouter


risk_router = APIRouter()



@risk_router.post("/risk_scoring")
def get_risk_scoring():
    return {"risk": "low"}



