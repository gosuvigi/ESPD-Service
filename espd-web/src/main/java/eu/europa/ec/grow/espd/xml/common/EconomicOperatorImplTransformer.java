package eu.europa.ec.grow.espd.xml.common;

import com.google.common.base.Function;
import eu.europa.ec.grow.espd.constants.enums.Country;
import eu.europa.ec.grow.espd.domain.EconomicOperatorImpl;
import eu.europa.ec.grow.espd.domain.EconomicOperatorRepresentative;
import eu.europa.ec.grow.espd.domain.PartyImpl;
import grow.names.specification.ubl.schema.xsd.espd_commonaggregatecomponents_1.EconomicOperatorPartyType;
import grow.names.specification.ubl.schema.xsd.espd_commonaggregatecomponents_1.NaturalPersonType;
import lombok.extern.slf4j.Slf4j;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ContactType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PersonType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PowerOfAttorneyType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by ratoico on 1/19/16 at 10:27 AM.
 */
@Component
@Slf4j
public class EconomicOperatorImplTransformer implements Function<EconomicOperatorPartyType, EconomicOperatorImpl> {

    private final PartyImplTransformer partyImplTransformer;

    @Autowired
    EconomicOperatorImplTransformer(PartyImplTransformer partyImplTransformer) {
        this.partyImplTransformer = partyImplTransformer;
    }

    @Override
    public EconomicOperatorImpl apply(EconomicOperatorPartyType input) {
        EconomicOperatorImpl impl = new EconomicOperatorImpl();

        if (input.getParty() != null) {
            PartyImpl party = partyImplTransformer.apply(input.getParty());
            impl.copyProperties(party);
        }

        if (input.getSMEIndicator() != null) {
            impl.setIsSmallSizedEnterprise(input.getSMEIndicator().isValue());
        }

        impl.setRepresentative(buildRepresentative(input));

        return impl;
    }

    private EconomicOperatorRepresentative buildRepresentative(EconomicOperatorPartyType input) {
        EconomicOperatorRepresentative representative = new EconomicOperatorRepresentative();

        if (CollectionUtils.isEmpty(input.getRepresentativeNaturalPerson())) {
            return representative;
        }

        NaturalPersonType naturalPersonType = input.getRepresentativeNaturalPerson().get(0);
        if (naturalPersonType.getNaturalPersonRoleDescription() != null) {
            representative.setPosition(trimToEmpty(naturalPersonType.getNaturalPersonRoleDescription().getValue()));
        }

        addPowerOfAttorneyInformation(representative, naturalPersonType.getPowerOfAttorney());

        return representative;
    }

    private void addPowerOfAttorneyInformation(EconomicOperatorRepresentative representative,
            PowerOfAttorneyType powerOfAttorney) {
        if (powerOfAttorney == null) {
            return;
        }

        if (CollectionUtils.isNotEmpty(powerOfAttorney.getDescription())) {
            representative.setAdditionalInfo(trimToEmpty(powerOfAttorney.getDescription().get(0).getValue()));
        }

        addAgentPartyInformation(representative, powerOfAttorney);
    }

    private void addAgentPartyInformation(EconomicOperatorRepresentative representative,
            PowerOfAttorneyType powerOfAttorney) {
        if (powerOfAttorney.getAgentParty() == null || CollectionUtils
                .isEmpty(powerOfAttorney.getAgentParty().getPerson())) {
            return;
        }

        PersonType personType = powerOfAttorney.getAgentParty().getPerson().get(0);

        if (personType.getFirstName() != null) {
            representative.setFirstName(trimToEmpty(personType.getFirstName().getValue()));
        }
        if (personType.getFamilyName() != null) {
            representative.setLastName(trimToEmpty(personType.getFamilyName().getValue()));
        }
        if (personType.getBirthDate() != null && personType.getBirthDate().getValue() != null) {
            representative.setDateOfBirth(personType.getBirthDate().getValue().toDate());
        }
        if (personType.getBirthplaceName() != null) {
            representative.setPlaceOfBirth(trimToEmpty(personType.getBirthplaceName().getValue()));
        }

        addResidenceAddressInformation(representative, personType.getResidenceAddress());
        addContactInformation(representative, personType.getContact());

    }

    private void addResidenceAddressInformation(EconomicOperatorRepresentative representative,
            AddressType residenceAddress) {
        if (residenceAddress == null) {
            return;
        }

        if (residenceAddress.getPostbox() != null) {
            representative.setPostalCode(trimToEmpty(residenceAddress.getPostbox().getValue()));
        }
        if (residenceAddress.getStreetName() != null) {
            representative.setStreet(trimToEmpty(residenceAddress.getStreetName().getValue()));
        }
        if (residenceAddress.getCityName() != null) {
            representative.setCity(trimToEmpty(residenceAddress.getCityName().getValue()));
        }
        if (residenceAddress.getCountry() != null && residenceAddress.getCountry().getIdentificationCode() != null) {
            Country country = Country
                    .findByIso2Code(trimToEmpty(residenceAddress.getCountry().getIdentificationCode().getValue()));
            representative.setCountry(country);
        }
    }

    private void addContactInformation(EconomicOperatorRepresentative representative, ContactType contact) {
        if (contact == null) {
            return;
        }
        if (contact.getElectronicMail() != null) {
            representative.setEmail(trimToEmpty(contact.getElectronicMail().getValue()));
        }
        if (contact.getTelephone() != null) {
            representative.setPhone(trimToEmpty(contact.getTelephone().getValue()));
        }
    }
}