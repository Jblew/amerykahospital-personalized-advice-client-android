package pl.jblew.ahpaaclient.data.model;

public class AdviceEntity {
  public String id;
  public String patientName;
  public String medicalprofessionalName;
  public String parentPhoneNumber;
  public String uid;
  public String advice;
  public String dateISO;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AdviceEntity that = (AdviceEntity) o;

    if (patientName != null ? !patientName.equals(that.patientName) : that.patientName != null) {
      return false;
    }
    if (medicalprofessionalName != null
        ? !medicalprofessionalName.equals(that.medicalprofessionalName)
        : that.medicalprofessionalName != null) {
      return false;
    }
    if (parentPhoneNumber != null
        ? !parentPhoneNumber.equals(that.parentPhoneNumber)
        : that.parentPhoneNumber != null) {
      return false;
    }
    if (uid != null ? !uid.equals(that.uid) : that.uid != null) {
      return false;
    }
    if (advice != null ? !advice.equals(that.advice) : that.advice != null) {
      return false;
    }
    return dateISO != null ? dateISO.equals(that.dateISO) : that.dateISO == null;
  }

  @Override
  public int hashCode() {
    int result = patientName != null ? patientName.hashCode() : 0;
    result =
        31 * result + (medicalprofessionalName != null ? medicalprofessionalName.hashCode() : 0);
    result = 31 * result + (parentPhoneNumber != null ? parentPhoneNumber.hashCode() : 0);
    result = 31 * result + (uid != null ? uid.hashCode() : 0);
    result = 31 * result + (advice != null ? advice.hashCode() : 0);
    result = 31 * result + (dateISO != null ? dateISO.hashCode() : 0);
    return result;
  }
}
