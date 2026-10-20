# The shape of the risk score data sent back to the Android app.

from pydantic import BaseModel


class AppRisk(BaseModel):
    package_name: str  # e.g. "com.waze"
    risk: str          # "low", "medium" or "high"
