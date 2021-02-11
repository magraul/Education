/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package me.teledon;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-04-21")
public class CazDTO implements org.apache.thrift.TBase<CazDTO, CazDTO._Fields>, java.io.Serializable, Cloneable, Comparable<CazDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CazDTO");

  private static final org.apache.thrift.protocol.TField DESCRIERE_FIELD_DESC = new org.apache.thrift.protocol.TField("descriere", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUMA_FIELD_DESC = new org.apache.thrift.protocol.TField("suma", org.apache.thrift.protocol.TType.DOUBLE, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CazDTOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CazDTOTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable String descriere; // required
  public double suma; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DESCRIERE((short)1, "descriere"),
    SUMA((short)2, "suma");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DESCRIERE
          return DESCRIERE;
        case 2: // SUMA
          return SUMA;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SUMA_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DESCRIERE, new org.apache.thrift.meta_data.FieldMetaData("descriere", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUMA, new org.apache.thrift.meta_data.FieldMetaData("suma", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CazDTO.class, metaDataMap);
  }

  public CazDTO() {
  }

  public CazDTO(
    String descriere,
    double suma)
  {
    this();
    this.descriere = descriere;
    this.suma = suma;
    setSumaIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CazDTO(CazDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDescriere()) {
      this.descriere = other.descriere;
    }
    this.suma = other.suma;
  }

  public CazDTO deepCopy() {
    return new CazDTO(this);
  }

  @Override
  public void clear() {
    this.descriere = null;
    setSumaIsSet(false);
    this.suma = 0.0;
  }

  @org.apache.thrift.annotation.Nullable
  public String getDescriere() {
    return this.descriere;
  }

  public CazDTO setDescriere(@org.apache.thrift.annotation.Nullable String descriere) {
    this.descriere = descriere;
    return this;
  }

  public void unsetDescriere() {
    this.descriere = null;
  }

  /** Returns true if field descriere is set (has been assigned a value) and false otherwise */
  public boolean isSetDescriere() {
    return this.descriere != null;
  }

  public void setDescriereIsSet(boolean value) {
    if (!value) {
      this.descriere = null;
    }
  }

  public double getSuma() {
    return this.suma;
  }

  public CazDTO setSuma(double suma) {
    this.suma = suma;
    setSumaIsSet(true);
    return this;
  }

  public void unsetSuma() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SUMA_ISSET_ID);
  }

  /** Returns true if field suma is set (has been assigned a value) and false otherwise */
  public boolean isSetSuma() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SUMA_ISSET_ID);
  }

  public void setSumaIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SUMA_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable Object value) {
    switch (field) {
    case DESCRIERE:
      if (value == null) {
        unsetDescriere();
      } else {
        setDescriere((String)value);
      }
      break;

    case SUMA:
      if (value == null) {
        unsetSuma();
      } else {
        setSuma((Double)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DESCRIERE:
      return getDescriere();

    case SUMA:
      return getSuma();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DESCRIERE:
      return isSetDescriere();
    case SUMA:
      return isSetSuma();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CazDTO)
      return this.equals((CazDTO)that);
    return false;
  }

  public boolean equals(CazDTO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_descriere = true && this.isSetDescriere();
    boolean that_present_descriere = true && that.isSetDescriere();
    if (this_present_descriere || that_present_descriere) {
      if (!(this_present_descriere && that_present_descriere))
        return false;
      if (!this.descriere.equals(that.descriere))
        return false;
    }

    boolean this_present_suma = true;
    boolean that_present_suma = true;
    if (this_present_suma || that_present_suma) {
      if (!(this_present_suma && that_present_suma))
        return false;
      if (this.suma != that.suma)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDescriere()) ? 131071 : 524287);
    if (isSetDescriere())
      hashCode = hashCode * 8191 + descriere.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(suma);

    return hashCode;
  }

  @Override
  public int compareTo(CazDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDescriere()).compareTo(other.isSetDescriere());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescriere()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.descriere, other.descriere);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuma()).compareTo(other.isSetSuma());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuma()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.suma, other.suma);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CazDTO(");
    boolean first = true;

    sb.append("descriere:");
    if (this.descriere == null) {
      sb.append("null");
    } else {
      sb.append(this.descriere);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("suma:");
    sb.append(this.suma);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CazDTOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CazDTOStandardScheme getScheme() {
      return new CazDTOStandardScheme();
    }
  }

  private static class CazDTOStandardScheme extends org.apache.thrift.scheme.StandardScheme<CazDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CazDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DESCRIERE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.descriere = iprot.readString();
              struct.setDescriereIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUMA
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.suma = iprot.readDouble();
              struct.setSumaIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CazDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.descriere != null) {
        oprot.writeFieldBegin(DESCRIERE_FIELD_DESC);
        oprot.writeString(struct.descriere);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SUMA_FIELD_DESC);
      oprot.writeDouble(struct.suma);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CazDTOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CazDTOTupleScheme getScheme() {
      return new CazDTOTupleScheme();
    }
  }

  private static class CazDTOTupleScheme extends org.apache.thrift.scheme.TupleScheme<CazDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CazDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDescriere()) {
        optionals.set(0);
      }
      if (struct.isSetSuma()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetDescriere()) {
        oprot.writeString(struct.descriere);
      }
      if (struct.isSetSuma()) {
        oprot.writeDouble(struct.suma);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CazDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.descriere = iprot.readString();
        struct.setDescriereIsSet(true);
      }
      if (incoming.get(1)) {
        struct.suma = iprot.readDouble();
        struct.setSumaIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

