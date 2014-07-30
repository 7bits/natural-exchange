package it.sevenbits.util.form;

/**
 * Created by evgeniy on 7/29/14.
 */
public class ExchangeForm {
    private Long idExchangeOwnerAdvertisement;
    private Long idExchangeOfferAdvertisement;
    private String exchangePropose;

    public void setIdExchangeOwnerAdvertisement(final Long idExchangeOwnerAdvertisement) {
        this.idExchangeOwnerAdvertisement = idExchangeOwnerAdvertisement;
    }

    public void setIdExchangeOfferAdvertisement(final Long idExchangeOfferAdvertisement) {
        this.idExchangeOfferAdvertisement = idExchangeOfferAdvertisement;
    }

    public void setExchangePropose(final String exchangePropose) {
        this.exchangePropose = exchangePropose;
    }

    public Long getIdExchangeOwnerAdvertisement() {
        return idExchangeOwnerAdvertisement;
    }

    public Long getIdExchangeOfferAdvertisement() {
        return idExchangeOfferAdvertisement;
    }

    public String getExchangePropose() {
        return exchangePropose;
    }
}
