Feature: Search
    
    Scenario: Search for Tharathorn Inphen
        Given I am at Google homepage
        When I search for "Tharathorn Inphen"
        Then I should see "Tharathorn Inphen Profiles | Facebook" as results