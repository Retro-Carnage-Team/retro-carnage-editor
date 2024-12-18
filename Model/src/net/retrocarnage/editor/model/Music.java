package net.retrocarnage.editor.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * A song that can be used as background music for a level.
 *
 * @author Thomas Werner
 */
public class Music extends Asset<Music> {

    @Override
    public Music deepCopy() {
        try {
            final ObjectMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xmlMapper.writeValueAsString(this), Music.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Music can't be serialized / deserialized", ex);
        }
    }

}
