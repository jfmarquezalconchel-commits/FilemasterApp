package com.filemasterapp.FilemasterApp.Utils;

import java.io.Serializable;

/**
 * Clase que mapea información de identificadores de bloques.
 * Se utiliza para almacenar información obtenida del comando lsblk,
 * incluyendo UUID, PARTUUID y PARTLABEL de dispositivos de almacenamiento.
 * <p>
 * Esta clase implementa Serializable para permitir la persistencia de objetos.
 * </p>
 */
public class BlkIdMapper implements Serializable {

    /**
     * UUID del dispositivo de bloque.
     */
    private String uuid;

    /**
     * PARTUUID del dispositivo de bloque.
     */
    private String partuuid;

    /**
     * LABEL del dispositivo de bloque.
     */
    private String partlabel;

    /**
     * Obtiene el UUID del dispositivo.
     *
     * @return el UUID del dispositivo
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Establece el UUID del dispositivo.
     *
     * @param uuid el UUID a establecer
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Obtiene el PARTUUID del dispositivo.
     *
     * @return el PARTUUID del dispositivo
     */
    public String getPartuuid() {
        return partuuid;
    }

    /**
     * Establece el PARTUUID del dispositivo.
     *
     * @param partuuid el PARTUUID a establecer
     */
    public void setPartuuid(String partuuid) {
        this.partuuid = partuuid;
    }

    /**
     * Obtiene el PARTLABEL del dispositivo.
     *
     * @return el PARTLABEL del dispositivo
     */
    public String getPartlabel() {
        return partlabel;
    }

    /**
     * Establece el PARTLABEL del dispositivo.
     *
     * @param partlabel el PARTLABEL a establecer
     */
    public void setPartlabel(String partlabel) {
        this.partlabel = partlabel;
    }

    /**
     * Obtiene el PARTLABEL del dispositivo (alias de getPartlabel).
     *
     * @return el PARTLABEL del dispositivo
     */
    public String getPartLabel() {
        return partlabel;
    }

    /**
     * Establece el PARTLABEL del dispositivo (alias de setPartlabel).
     *
     * @param partLabel el PARTLABEL a establecer
     */
    public void setPartLabel(String partLabel) {
        this.partlabel = partLabel;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     * Formato: uuid|partuuid|partlabel
     *
     * @return cadena con los identificadores separados por pipe
     */
    @Override
    public String toString() {
        return this.uuid + "|" + this.partuuid + "|" + this.partlabel;
    }

}
