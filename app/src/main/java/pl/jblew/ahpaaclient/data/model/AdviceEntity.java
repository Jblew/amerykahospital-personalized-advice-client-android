/***
 *     Ameryka Hospital Personalized advice system — the Android client app. This system allows
 *     doctors to securely and reliably send medical advices to patient's smartphones.
 *
 *     Copyright (C) 2019 by Jędrzej Lewandowski <jedrzejblew@gmail.com>
 *         (https://jedrzej.lewandowski.doctor)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
