//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.23 at 08:43:01 PM IST 
//


package com.jeet.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.jeet.com/api}userId"/>
 *         &lt;element ref="{http://www.jeet.com/api}pipe"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userId",
    "pipe"
})
@XmlRootElement(name = "userPipe")
public class UserPipe {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected Pipe pipe;
    
    

    public UserPipe(String userId, Pipe pipe) {
		super();
		this.userId = userId;
		this.pipe = pipe;
	}

	/**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the pipe property.
     * 
     * @return
     *     possible object is
     *     {@link Pipe }
     *     
     */
    public Pipe getPipe() {
        return pipe;
    }

    /**
     * Sets the value of the pipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pipe }
     *     
     */
    public void setPipe(Pipe value) {
        this.pipe = value;
    }

}
