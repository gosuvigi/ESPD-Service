package eu.europa.ec.grow.espd.xml.request.exclusion

import eu.europa.ec.grow.espd.xml.base.AbstractExclusionCriteriaFixture
import eu.europa.ec.grow.espd.domain.*
/**
 *  Created by vigi on 11/17/15:3:54 PM.
 */
class EspdRequestExclusionCriteriaMarshallingTest extends AbstractExclusionCriteriaFixture {

    def "criteria should appear only if they were selected in the ESPD UI"() {
        given:
        def espd = new EspdDocument(criminalConvictions: new CriminalConvictionsCriterion(exists: true),
                paymentTaxes: new TaxesCriterion(exists: true), corruption: new CriminalConvictionsCriterion(exists: true))

        when:
        def request = parseRequestXml(espd)

        then: "only one criterion response per criterion"
        request.Criterion.size() == 3
        checkCriterionId(request, 0, "005eb9ed-1347-4ca3-bb29-9bc0db64e1ab")
        checkCriterionId(request, 1, "c27b7c4e-c837-4529-b867-ed55ce639db5")
        checkCriterionId(request, 2, "b61bbeb7-690e-4a40-bc68-d6d4ecfaa3d4")
    }

    def "all exclusion criteria should be in the correct order"() {
        given:
        def espd = new EspdDocument(
                criminalConvictions: new CriminalConvictionsCriterion(exists: true),
                corruption: new CriminalConvictionsCriterion(exists: true),
                fraud: new CriminalConvictionsCriterion(exists: true),
                terroristOffences: new CriminalConvictionsCriterion(exists: true),
                moneyLaundering: new CriminalConvictionsCriterion(exists: true),
                childLabour: new CriminalConvictionsCriterion(exists: true),
                paymentTaxes: new TaxesCriterion(exists: true),
                paymentSocialSecurity: new TaxesCriterion(exists: true),
                breachingObligationsEnvironmental: new LawCriterion(exists: true),
                bankruptcy: new BankruptcyCriterion(exists: true),
                insolvency: new BankruptcyCriterion(exists: true),
                arrangementWithCreditors: new BankruptcyCriterion(exists: true),
                analogousSituation: new BankruptcyCriterion(exists: true),
                assetsAdministeredByLiquidator: new BankruptcyCriterion(exists: true),
                businessActivitiesSuspended: new BankruptcyCriterion(exists: true),
                guiltyGrave: new MisconductCriterion(exists: true),
                conflictInterest: new ConflictInterestCriterion(exists: true),
                involvementPreparationProcurement: new ConflictInterestCriterion(exists: true),
                earlyTermination: new ConflictInterestCriterion(exists: true),
                guiltyMisinterpretation: new ConflictInterestCriterion(exists: true),
                purelyNationalGrounds: new PurelyNationalGrounds(exists: true))

        when:
        def request = parseRequestXml(espd)

        then:
        request.Criterion.size() == 21
        checkCriterionId(request, 0, "005eb9ed-1347-4ca3-bb29-9bc0db64e1ab")
        checkCriterionId(request, 1, "c27b7c4e-c837-4529-b867-ed55ce639db5")
        checkCriterionId(request, 2, "297d2323-3ede-424e-94bc-a91561e6f320")
        checkCriterionId(request, 3, "d486fb70-86b3-4e75-97f2-0d71b5697c7d")
        checkCriterionId(request, 4, "47112079-6fec-47a3-988f-e561668c3aef")
        checkCriterionId(request, 5, "d789d01a-fe03-4ccd-9898-73f9cfa080d1")
        checkCriterionId(request, 6, "b61bbeb7-690e-4a40-bc68-d6d4ecfaa3d4")
        checkCriterionId(request, 7, "7d85e333-bbab-49c0-be8d-c36d71a72f5e")
        checkCriterionId(request, 8, "a80ddb62-d25b-4e4e-ae22-3968460dc0a9")
        checkCriterionId(request, 9, "d3732c09-7d62-4edc-a172-241da6636e7c")
        checkCriterionId(request, 10, "396f288a-e267-4c20-851a-ed4f7498f137")
        checkCriterionId(request, 11, "68918c7a-f5bc-4a1a-a62f-ad8983600d48")
        checkCriterionId(request, 12, "daffa2a9-9f8f-4568-8be8-7b8bf306d096")
        checkCriterionId(request, 13, "8fda202a-0c37-41bb-9d7d-de3f49edbfcb")
        checkCriterionId(request, 14, "166536e2-77f7-455c-b018-70582474e4f6")
        checkCriterionId(request, 15, "514d3fde-1e3e-4dcd-b02a-9f984d5bbda3")
        checkCriterionId(request, 16, "b1b5ac18-f393-4280-9659-1367943c1a2e")
        checkCriterionId(request, 17, "61874050-5130-4f1c-a174-720939c7b483")
        checkCriterionId(request, 18, "3293e92b-7f3e-42f1-bee6-a7641bb04251")
        checkCriterionId(request, 19, "696a75b2-6107-428f-8b74-82affb67e184")
        checkCriterionId(request, 20, "63adb07d-db1b-4ef0-a14e-a99785cf8cf6")
    }

}