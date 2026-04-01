package com.filemasterapp.FilemasterApp.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que gestiona la interacción con el sistema de archivos del sistema operativo.
 * <p>
 * Proporciona métodos para obtener información sobre sistemas de archivos montados,
 * capacidad de almacenamiento y mapeo de identificadores de bloques.
 * </p>
 */
class FindmntResult {

    /**
     * Lista de sistemas de archivos montados.
     */
    public ArrayList<FileSystemMapper> filesystems = new ArrayList<>();
}

/**
 * Clase que gestiona la interacción con el sistema de archivos del sistema operativo.
 * <p>
 * Proporciona métodos para obtener información sobre sistemas de archivos montados,
 * capacidad de almacenamiento y mapeo de identificadores de bloques.
 * </p>
 */
public class FileSystemHandler {

    /**
     * Comando findmnt para obtener información de sistemas de archivos montados.
     */
    private static final String mountCommand = "/usr/bin/findmnt";

    /**
     * Parámetro JSON para findmnt.
     */
    private static final String mountParam = "-J";

    /**
     * Comando df para obtener información de capacidad de almacenamiento.
     */
    private static final String getCapacityCommand = """
                    /usr/bin/df --output=source,size,used,avail,pcent,target  | /usr/bin/tail -n +2 | /usr/bin/jq -R 'split(" ") | map(select(length>0)) |
                    {filesystem: .[0],size: .[1],used: .[2],avail: .[3],use_percent: .[4],mounted_on: .[5] }' """;

    /**
     * Comando lsblk para obtener información de identificadores de bloques.
     */
    private static String getBlkidCommand = "/usr/bin/lsblk -o UUID,PARTUUID,PARTLABEL -J ";

    /**
     * Comando jq para extraer el primer dispositivo de lsblk.
     */
    private static String getBlkidCommand2 = "|/usr/bin/jq '.blockdevices[0]'";

    /**
     * Obtiene información de identificadores de bloques para un dispositivo específico.
     * <p>
     * Ejecuta el comando lsblk para el dispositivo especificado y extrae
     * UUID, PARTUUID y PARTLABEL.
     * </p>
     *
     * @param device el dispositivo para el cual obtener la información (ej: /dev/sda1)
     * @return un BlkIdMapper con la información del dispositivo, o un objeto vacío si falla
     */
    public static BlkIdMapper FileSystemBlkInfo(String device) {
        String outputJson = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS);
        mapper.enable(com.fasterxml.jackson.core.JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);

        BlkIdMapper blockIdMapper = new BlkIdMapper();

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c",
                    getBlkidCommand + device + getBlkidCommand2);
            pb.redirectErrorStream(true); // mezcla stdout y stderr
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                outputJson += line + "\n";
            }
            blockIdMapper = mapper.readValue(outputJson, BlkIdMapper.class);

            int exitCode = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blockIdMapper;
    }

    /**
     * Obtiene la capacidad de todos los sistemas de archivos montados.
     * <p>
     * Ejecuta el comando df para obtener información sobre tamaño,
     * espacio usado, espacio disponible y porcentaje de uso para cada
     * sistema de archivos montado.
     * </p>
     *
     * @return un HashMap donde la clave es el punto de montaje y el valor es
     *         la información de capacidad del sistema de archivos
     */
    public static HashMap<String, CapacityMapper> FileSystemCapacity() {
        String outputJson = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS);
        mapper.enable(com.fasterxml.jackson.core.JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);

        HashMap<String, CapacityMapper> list = new HashMap<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", getCapacityCommand);
            pb.redirectErrorStream(true); // mezcla stdout y stderr
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                outputJson += line + "\n";
            }

            String[] bloques = outputJson.split("}");
            ArrayList<CapacityMapper> capacities = new ArrayList<>();

            for (String bloque : bloques) {
                bloque = bloque.replace("\n", "");
                if (bloque.length() > 0) {
                    bloque = bloque + "}";
                    capacities.add(mapper.readValue(bloque, CapacityMapper.class));
                }
            }

            for (CapacityMapper capacity : capacities) {
                list.put(capacity.getMounted_on(), capacity);
            }
            int exitCode = process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Obtiene una lista de todos los sistemas de archivos montados.
     * <p>
     * Ejecuta el comando findmnt con salida JSON para obtener información
     * detallada sobre cada sistema de archivo montado, incluyendo tipo,
     * opciones y sistemas de archivos hijos.
     * </p>
     *
     * @return una lista de FileSystemMapper con la información de cada sistema de archivos
     */
    public static List<FileSystemMapper> FileSystemReader() {
        String outputJson = "";
        ObjectMapper mapper = new ObjectMapper();
        List<FileSystemMapper> lista = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(mountCommand, mountParam);
            pb.redirectErrorStream(true); // mezcla stdout y stderr

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                outputJson += line + "\n";
            }

            int exitCode = process.waitFor();
            FindmntResult result = mapper.readValue(outputJson, FindmntResult.class);

            for (FileSystemMapper fs : result.filesystems) {
                lista.add(fs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}
