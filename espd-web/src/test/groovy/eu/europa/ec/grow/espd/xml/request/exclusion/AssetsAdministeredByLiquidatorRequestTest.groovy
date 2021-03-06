/*
 *
 * Copyright 2016 EUROPEAN COMMISSION
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */

package eu.europa.ec.grow.espd.xml.request.exclusion

import eu.europa.ec.grow.espd.domain.enums.criteria.ExclusionCriterion
import eu.europa.ec.grow.espd.domain.BankruptcyCriterion
import eu.europa.ec.grow.espd.domain.EspdDocument
import eu.europa.ec.grow.espd.xml.base.AbstractExclusionCriteriaFixture
/**
 * Created by ratoico on 12/9/15 at 1:19 PM.
 */
class AssetsAdministeredByLiquidatorRequestTest extends AbstractExclusionCriteriaFixture {

    def "16. should contain the 'Assets being administered by liquidator' criterion"() {
        given:
        def espd = new EspdDocument(assetsAdministeredByLiquidator: new BankruptcyCriterion(exists: true))

        when:
        def request = parseRequestXml(espd)
        def idx = getRequestCriterionIndex(ExclusionCriterion.ASSETS_ADMINISTERED_BY_LIQUIDATOR)

        then: "CriterionID element"
        checkCriterionId(request, idx, "8fda202a-0c37-41bb-9d7d-de3f49edbfcb")

        then: "CriterionTypeCode element"
        checkCriterionTypeCode(request, idx, "CRITERION.EXCLUSION.BUSINESS.LIQUIDATOR_ADMINISTERED")

        then: "CriterionName element"
        request.Criterion[idx].Name.text() == "Assets being administered by liquidator"

        then: "CriterionDescription element"
        request.Criterion[idx].Description.text() == "Are the assets of the economic operator being administered by a liquidator or by the court?  This information needs not be given if exclusion of economic operators in this case has been made mandatory under the applicable national law without any possibility of derogation where the economic operator is nevertheless able to perform the contract."

        then: "CriterionLegislationReference element"
        checkLegislationReference(request, idx, "57(4)")

        then: "check all the sub groups"
        request.Criterion[idx].RequirementGroup.size() == 2

        then: "main sub group"
        def g1 = request.Criterion[idx].RequirementGroup[0]
        g1.ID.text() == "d91c11a1-f19e-4b83-8ade-c4be2bf00555"
        g1.@pi.text() == ""
        g1.RequirementGroup.size() == 1
        g1.Requirement.size() == 1

        then: "main sub group requirements"
        def r1_0 = g1.Requirement[0]
        checkRequirement(r1_0, "974c8196-9d1c-419c-9ca9-45bb9f5fd59a", "Your answer?", "INDICATOR")

        then:
        def g1_1 = g1.RequirementGroup[0]
        g1_1.ID.text() == "aeef523b-c8fc-4dba-9c34-03e34812567b"
        g1_1.@pi.text() == "GROUP_FULFILLED.ON_TRUE"
        def r1_1 = g1_1.Requirement[0]
        checkRequirement(r1_1, "e098da8e-4717-4500-965f-f882d5b4e1ad", "Please describe them", "DESCRIPTION")

        def r1_2 = g1_1.Requirement[1]
        checkRequirement(r1_2, "4e3f468a-86c4-4c99-bd15-c8b221229348", "Indicate reasons for being nevertheless to perform the contract", "DESCRIPTION")

        then: "check second sub group"
        def sub2 = request.Criterion[idx].RequirementGroup[1]
        checkInfoAvailableElectronicallyRequirementGroup(sub2)
    }

}