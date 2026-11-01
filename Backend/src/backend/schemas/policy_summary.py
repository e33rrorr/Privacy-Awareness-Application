from pydantic import BaseModel

# Policy summary schema
class PolicySection(BaseModel):
    key: str           
    question: str      
    points: list[str]  


# Full policy summary for an app
class PolicySummary(BaseModel):
    package_name: str                    
    summary: list[PolicySection] | None  
    policy_url: str | None               
