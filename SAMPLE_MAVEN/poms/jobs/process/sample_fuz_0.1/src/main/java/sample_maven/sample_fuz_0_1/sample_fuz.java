
package sample_maven.sample_fuz_0_1;

import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.DataQualityDependencies;
import routines.Mathematical;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.DQTechnical;
import routines.MDM;
import routines.StringHandling;
import routines.DataMasking;
import routines.TalendDate;
import routines.DqStringHandling;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: sample_fuz Purpose: <br>
 * Description: <br>
 * 
 * @author jgavina@solvento.local
 * @version 7.3.1.20210623_0656-patch
 * @status
 */
public class sample_fuz implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "sample_fuz.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(sample_fuz.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "sample_fuz";
	private final String projectName = "SAMPLE_MAVEN";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_aIA6sCGkEeyMN-1jqBsHuQ", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					sample_fuz.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(sample_fuz.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMatchGroup_1_GroupOut_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		tMatchGroup_1_GroupIn_error(exception, errorComponent, globalMap);

	}

	public void tMatchGroup_1_GroupIn_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class ContextBean {
		static String evaluate(String context, String contextExpression)
				throws IOException, javax.script.ScriptException {
			boolean isExpression = contextExpression.contains("+") || contextExpression.contains("(");
			final String prefix = isExpression ? "\"" : "";
			java.util.Properties defaultProps = new java.util.Properties();
			java.io.InputStream inContext = sample_fuz.class.getClassLoader()
					.getResourceAsStream("sample_maven/sample_fuz_0_1/contexts/" + context + ".properties");
			if (inContext == null) {
				inContext = sample_fuz.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + context + ".properties");
			}
			try {
				defaultProps.load(inContext);
			} finally {
				inContext.close();
			}
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("context.([\\w]+)");
			java.util.regex.Matcher matcher = pattern.matcher(contextExpression);

			while (matcher.find()) {
				contextExpression = contextExpression.replaceAll(matcher.group(0),
						prefix + defaultProps.getProperty(matcher.group(1)) + prefix);
			}
			if (contextExpression.startsWith("/services")) {
				contextExpression = contextExpression.replaceFirst("/services", "");
			}
			return isExpression ? evaluateContextExpression(contextExpression) : contextExpression;
		}

		public static String evaluateContextExpression(String expression) throws javax.script.ScriptException {
			javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
			javax.script.ScriptEngine engine = manager.getEngineByName("nashorn");
			// Add some import for Java
			expression = expression.replaceAll("System.getProperty", "java.lang.System.getProperty");
			return engine.eval(expression).toString();
		}

		public static String getContext(String context, String contextName, String jobName) throws Exception {

			String currentContext = null;
			String jobNameStripped = jobName.substring(jobName.lastIndexOf(".") + 1);

			boolean inOSGi = routines.system.BundleUtils.inOSGi();

			if (inOSGi) {
				java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils
						.getJobProperties(jobNameStripped);

				if (jobProperties != null) {
					currentContext = (String) jobProperties.get("context");
				}
			}

			return contextName.contains("context.")
					? evaluate(currentContext == null ? context : currentContext, contextName)
					: contextName;
		}
	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_SAMPLE_MAVEN_sample_fuz = new byte[0];
		static byte[] commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[0];

		public String Emp_ID;

		public String getEmp_ID() {
			return this.Emp_ID;
		}

		public String Name_Prefix;

		public String getName_Prefix() {
			return this.Name_Prefix;
		}

		public String First_Name;

		public String getFirst_Name() {
			return this.First_Name;
		}

		public String Middle_Initial;

		public String getMiddle_Initial() {
			return this.Middle_Initial;
		}

		public String Last_Name;

		public String getLast_Name() {
			return this.Last_Name;
		}

		public String Gender;

		public String getGender() {
			return this.Gender;
		}

		public String E_Mail;

		public String getE_Mail() {
			return this.E_Mail;
		}

		public String GID;

		public String getGID() {
			return this.GID;
		}

		public Integer GRP_SIZE;

		public Integer getGRP_SIZE() {
			return this.GRP_SIZE;
		}

		public Boolean MASTER;

		public Boolean getMASTER() {
			return this.MASTER;
		}

		public Double SCORE;

		public Double getSCORE() {
			return this.SCORE;
		}

		public Double GRP_QUALITY;

		public Double getGRP_QUALITY() {
			return this.GRP_QUALITY;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_SAMPLE_MAVEN_sample_fuz.length) {
					if (length < 1024 && commonByteArray_SAMPLE_MAVEN_sample_fuz.length == 0) {
						commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[1024];
					} else {
						commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length);
				strReturn = new String(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_SAMPLE_MAVEN_sample_fuz) {

				try {

					int length = 0;

					this.Emp_ID = readString(dis);

					this.Name_Prefix = readString(dis);

					this.First_Name = readString(dis);

					this.Middle_Initial = readString(dis);

					this.Last_Name = readString(dis);

					this.Gender = readString(dis);

					this.E_Mail = readString(dis);

					this.GID = readString(dis);

					this.GRP_SIZE = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.MASTER = null;
					} else {
						this.MASTER = dis.readBoolean();
					}

					length = dis.readByte();
					if (length == -1) {
						this.SCORE = null;
					} else {
						this.SCORE = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.GRP_QUALITY = null;
					} else {
						this.GRP_QUALITY = dis.readDouble();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Emp_ID, dos);

				// String

				writeString(this.Name_Prefix, dos);

				// String

				writeString(this.First_Name, dos);

				// String

				writeString(this.Middle_Initial, dos);

				// String

				writeString(this.Last_Name, dos);

				// String

				writeString(this.Gender, dos);

				// String

				writeString(this.E_Mail, dos);

				// String

				writeString(this.GID, dos);

				// Integer

				writeInteger(this.GRP_SIZE, dos);

				// Boolean

				if (this.MASTER == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.MASTER);
				}

				// Double

				if (this.SCORE == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.SCORE);
				}

				// Double

				if (this.GRP_QUALITY == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.GRP_QUALITY);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Emp_ID=" + Emp_ID);
			sb.append(",Name_Prefix=" + Name_Prefix);
			sb.append(",First_Name=" + First_Name);
			sb.append(",Middle_Initial=" + Middle_Initial);
			sb.append(",Last_Name=" + Last_Name);
			sb.append(",Gender=" + Gender);
			sb.append(",E_Mail=" + E_Mail);
			sb.append(",GID=" + GID);
			sb.append(",GRP_SIZE=" + String.valueOf(GRP_SIZE));
			sb.append(",MASTER=" + String.valueOf(MASTER));
			sb.append(",SCORE=" + String.valueOf(SCORE));
			sb.append(",GRP_QUALITY=" + String.valueOf(GRP_QUALITY));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Emp_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(Emp_ID);
			}

			sb.append("|");

			if (Name_Prefix == null) {
				sb.append("<null>");
			} else {
				sb.append(Name_Prefix);
			}

			sb.append("|");

			if (First_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(First_Name);
			}

			sb.append("|");

			if (Middle_Initial == null) {
				sb.append("<null>");
			} else {
				sb.append(Middle_Initial);
			}

			sb.append("|");

			if (Last_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Last_Name);
			}

			sb.append("|");

			if (Gender == null) {
				sb.append("<null>");
			} else {
				sb.append(Gender);
			}

			sb.append("|");

			if (E_Mail == null) {
				sb.append("<null>");
			} else {
				sb.append(E_Mail);
			}

			sb.append("|");

			if (GID == null) {
				sb.append("<null>");
			} else {
				sb.append(GID);
			}

			sb.append("|");

			if (GRP_SIZE == null) {
				sb.append("<null>");
			} else {
				sb.append(GRP_SIZE);
			}

			sb.append("|");

			if (MASTER == null) {
				sb.append("<null>");
			} else {
				sb.append(MASTER);
			}

			sb.append("|");

			if (SCORE == null) {
				sb.append("<null>");
			} else {
				sb.append(SCORE);
			}

			sb.append("|");

			if (GRP_QUALITY == null) {
				sb.append("<null>");
			} else {
				sb.append(GRP_QUALITY);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_SAMPLE_MAVEN_sample_fuz = new byte[0];
		static byte[] commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[0];

		public String Emp_ID;

		public String getEmp_ID() {
			return this.Emp_ID;
		}

		public String Name_Prefix;

		public String getName_Prefix() {
			return this.Name_Prefix;
		}

		public String First_Name;

		public String getFirst_Name() {
			return this.First_Name;
		}

		public String Middle_Initial;

		public String getMiddle_Initial() {
			return this.Middle_Initial;
		}

		public String Last_Name;

		public String getLast_Name() {
			return this.Last_Name;
		}

		public String Gender;

		public String getGender() {
			return this.Gender;
		}

		public String E_Mail;

		public String getE_Mail() {
			return this.E_Mail;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_SAMPLE_MAVEN_sample_fuz.length) {
					if (length < 1024 && commonByteArray_SAMPLE_MAVEN_sample_fuz.length == 0) {
						commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[1024];
					} else {
						commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length);
				strReturn = new String(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_SAMPLE_MAVEN_sample_fuz) {

				try {

					int length = 0;

					this.Emp_ID = readString(dis);

					this.Name_Prefix = readString(dis);

					this.First_Name = readString(dis);

					this.Middle_Initial = readString(dis);

					this.Last_Name = readString(dis);

					this.Gender = readString(dis);

					this.E_Mail = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Emp_ID, dos);

				// String

				writeString(this.Name_Prefix, dos);

				// String

				writeString(this.First_Name, dos);

				// String

				writeString(this.Middle_Initial, dos);

				// String

				writeString(this.Last_Name, dos);

				// String

				writeString(this.Gender, dos);

				// String

				writeString(this.E_Mail, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Emp_ID=" + Emp_ID);
			sb.append(",Name_Prefix=" + Name_Prefix);
			sb.append(",First_Name=" + First_Name);
			sb.append(",Middle_Initial=" + Middle_Initial);
			sb.append(",Last_Name=" + Last_Name);
			sb.append(",Gender=" + Gender);
			sb.append(",E_Mail=" + E_Mail);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Emp_ID == null) {
				sb.append("<null>");
			} else {
				sb.append(Emp_ID);
			}

			sb.append("|");

			if (Name_Prefix == null) {
				sb.append("<null>");
			} else {
				sb.append(Name_Prefix);
			}

			sb.append("|");

			if (First_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(First_Name);
			}

			sb.append("|");

			if (Middle_Initial == null) {
				sb.append("<null>");
			} else {
				sb.append(Middle_Initial);
			}

			sb.append("|");

			if (Last_Name == null) {
				sb.append("<null>");
			} else {
				sb.append(Last_Name);
			}

			sb.append("|");

			if (Gender == null) {
				sb.append("<null>");
			} else {
				sb.append(Gender);
			}

			sb.append("|");

			if (E_Mail == null) {
				sb.append("<null>");
			} else {
				sb.append(E_Mail);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;
		String currentVirtualComponent = null;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();

				/**
				 * [tMatchGroup_1_GroupOut begin ] start
				 */

				ok_Hash.put("tMatchGroup_1_GroupOut", false);
				start_Hash.put("tMatchGroup_1_GroupOut", System.currentTimeMillis());

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupOut";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tMatchGroup_1_GroupOut = 0;

				if (log.isDebugEnabled())
					log.debug("tMatchGroup_1_GroupOut - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMatchGroup_1_GroupOut {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMatchGroup_1_GroupOut = new StringBuilder();
							log4jParamters_tMatchGroup_1_GroupOut.append("Parameters:");
							log4jParamters_tMatchGroup_1_GroupOut
									.append("MATCHING_ALGORITHM" + " = " + "Simple VSR Matcher");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut.append("STORE_ON_DISK" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut.append("SEPARATE_OUTPUT" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut.append("LINK_WITH_PREVIOUS" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut.append("SORT_DATA" + " = " + "true");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut.append("OUTPUTDD" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							log4jParamters_tMatchGroup_1_GroupOut
									.append("DEACTIVATE_MATCHING_COMPUTATION" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupOut.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMatchGroup_1_GroupOut - " + (log4jParamters_tMatchGroup_1_GroupOut));
						}
					}
					new BytesLimit65535_tMatchGroup_1_GroupOut().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMatchGroup_1_GroupOut", "tMatchGroup_1_GroupOut", "tMatchGroupOut");
					talendJobLogProcess(globalMap);
				}

				class tMatchGroup_1Struct implements routines.system.IPersistableRow<tMatchGroup_1Struct> {
					final byte[] commonByteArrayLock_SAMPLE_MAVEN_sample_fuz = new byte[0];
					byte[] commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[0];
					public String Emp_ID;

					public String getEmp_ID() {
						return this.Emp_ID;
					}

					public String Name_Prefix;

					public String getName_Prefix() {
						return this.Name_Prefix;
					}

					public String First_Name;

					public String getFirst_Name() {
						return this.First_Name;
					}

					public String Middle_Initial;

					public String getMiddle_Initial() {
						return this.Middle_Initial;
					}

					public String Last_Name;

					public String getLast_Name() {
						return this.Last_Name;
					}

					public String Gender;

					public String getGender() {
						return this.Gender;
					}

					public String E_Mail;

					public String getE_Mail() {
						return this.E_Mail;
					}

					public void copyDateToOut(row2Struct other) {
						other.Emp_ID = this.Emp_ID;
						other.Name_Prefix = this.Name_Prefix;
						other.First_Name = this.First_Name;
						other.Middle_Initial = this.Middle_Initial;
						other.Last_Name = this.Last_Name;
						other.Gender = this.Gender;
						other.E_Mail = this.E_Mail;
					}

					private String readString(ObjectInputStream dis) throws IOException {
						String strReturn = null;
						int length = 0;
						length = dis.readInt();

						if (length == -1) {
							strReturn = null;
						} else {
							if (length > commonByteArray_SAMPLE_MAVEN_sample_fuz.length) {
								if (length < 1024 && commonByteArray_SAMPLE_MAVEN_sample_fuz.length == 0) {
									commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[1024];
								} else {
									commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[2 * length];
								}
							}
							dis.readFully(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length);
							strReturn = new String(commonByteArray_SAMPLE_MAVEN_sample_fuz, 0, length, utf8Charset);
						}
						return strReturn;
					}

					private void writeString(String str, ObjectOutputStream dos) throws IOException {
						if (str == null) {
							dos.writeInt(-1);
						} else {
							byte[] byteArray = str.getBytes(utf8Charset);
							dos.writeInt(byteArray.length);
							dos.write(byteArray);
						}
					}

					public void readData(ObjectInputStream dis) {
						synchronized (commonByteArrayLock_SAMPLE_MAVEN_sample_fuz) {
							try {
								int length = 0;
								this.Emp_ID = readString(dis);
								this.Name_Prefix = readString(dis);
								this.First_Name = readString(dis);
								this.Middle_Initial = readString(dis);
								this.Last_Name = readString(dis);
								this.Gender = readString(dis);
								this.E_Mail = readString(dis);
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
						}
					}

					public void writeData(ObjectOutputStream dos) {
						try {
							// String
							writeString(this.Emp_ID, dos);
							// String
							writeString(this.Name_Prefix, dos);
							// String
							writeString(this.First_Name, dos);
							// String
							writeString(this.Middle_Initial, dos);
							// String
							writeString(this.Last_Name, dos);
							// String
							writeString(this.Gender, dos);
							// String
							writeString(this.E_Mail, dos);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}

					public String toString() {

						StringBuilder sb = new StringBuilder();
						sb.append(super.toString());
						sb.append("[");
						sb.append("," + "Emp_ID=" + Emp_ID);
						sb.append("," + "Name_Prefix=" + Name_Prefix);
						sb.append("," + "First_Name=" + First_Name);
						sb.append("," + "Middle_Initial=" + Middle_Initial);
						sb.append("," + "Last_Name=" + Last_Name);
						sb.append("," + "Gender=" + Gender);
						sb.append("," + "E_Mail=" + E_Mail);
						sb.append("]");
						return sb.toString();
					}

					/**
					 * Compare keys
					 */
					public int compareTo(tMatchGroup_1Struct other) {
						int returnValue = -1;
						return returnValue;
					}

					private int checkNullsAndCompare(Object object1, Object object2) {
						int returnValue = 0;
						if (object1 instanceof Comparable && object2 instanceof Comparable) {
							returnValue = ((Comparable) object1).compareTo(object2);
						} else if (object1 != null && object2 != null) {
							returnValue = compareStrings(object1.toString(), object2.toString());
						} else if (object1 == null && object2 != null) {
							returnValue = 1;
						} else if (object1 != null && object2 == null) {
							returnValue = -1;
						} else {
							returnValue = 0;
						}
						return returnValue;
					}

					private int compareStrings(String string1, String string2) {
						return string1.compareTo(string2);
					}
				}

				class tMatchGroup_1_2Struct
						implements routines.system.IPersistableComparableLookupRow<tMatchGroup_1_2Struct>,
						org.talend.dataquality.indicators.mapdb.helper.IObjectConvertArray {
					final byte[] commonByteArrayLock_SAMPLE_MAVEN_sample_fuz = new byte[0];
					byte[] commonByteArray_SAMPLE_MAVEN_sample_fuz = new byte[0];
					private final int DEFAULT_HASHCODE = 1;
					private final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					public void copyDateToOut(tMatchGroup_1_2Struct other) {
					}

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final tMatchGroup_1_2Struct other = (tMatchGroup_1_2Struct) obj;

						return true;
					}

					public void copyDataTo(tMatchGroup_1_2Struct other) {
					}

					public void copyKeysDataTo(tMatchGroup_1_2Struct other) {
					}

					public void readKeysData(ObjectInputStream dis) {
						synchronized (commonByteArrayLock_SAMPLE_MAVEN_sample_fuz) {
							try {
								int length = 0;
							} finally {
							}
						}
					}

					public void writeKeysData(ObjectOutputStream dos) {
						try {
						} finally {
						}
					}

					/**
					 * Fill Values data by reading ObjectInputStream.
					 */
					public void readValuesData(DataInputStream dis, ObjectInputStream ois) {

					}

					/**
					 * Return a byte array which represents Values data.
					 */
					public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {

					}

					public String toString() {

						StringBuilder sb = new StringBuilder();
						sb.append(super.toString());
						sb.append("[");
						sb.append("]");
						return sb.toString();
					}

					/**
					 * Compare keys
					 */
					public int compareTo(tMatchGroup_1_2Struct other) {
						int returnValue = -1;
						return returnValue;
					}

					private int checkNullsAndCompare(Object object1, Object object2) {
						int returnValue = 0;
						if (object1 instanceof Comparable && object2 instanceof Comparable) {
							returnValue = ((Comparable) object1).compareTo(object2);
						} else if (object1 != null && object2 != null) {
							returnValue = compareStrings(object1.toString(), object2.toString());
						} else if (object1 == null && object2 != null) {
							returnValue = 1;
						} else if (object1 != null && object2 == null) {
							returnValue = -1;
						} else {
							returnValue = 0;
						}
						return returnValue;
					}

					private int compareStrings(String string1, String string2) {
						return string1.compareTo(string2);
					}

					@Override
					public Object[] getArrays() {
						Object[] array = new Object[0];
						return array;
					}

					@Override
					public void restoreObjectByArrays(Object[] elements) {
					}
				}// end of _2struct

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_tMatchGroup_1 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.ALL_ROWS;
				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<tMatchGroup_1Struct> tHash_Lookup_row1 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<tMatchGroup_1Struct>getLookup(matchingModeEnum_tMatchGroup_1);
				globalMap.put("tHash_Lookup_row1", tHash_Lookup_row1);

				/* store all block rows */
				java.util.Map<tMatchGroup_1_2Struct, String> blockRows_row1 = new java.util.HashMap<tMatchGroup_1_2Struct, String>();

				/**
				 * [tMatchGroup_1_GroupOut begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
							log4jParamters_tFileInputDelimited_1.append("Parameters:");
							log4jParamters_tFileInputDelimited_1.append("FILENAME" + " = "
									+ "\"C:/Users/Solvento/Desktop/PNB/Talend testing/sample_with_dup.csv\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CSV_OPTION" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FIELDSEPARATOR" + " = " + "\",\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("HEADER" + " = " + "1");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("FOOTER" + " = " + "0");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("REMOVE_EMPTY_ROW" + " = " + "true");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("UNCOMPRESS" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("RANDOM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMALL" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("TRIMSELECT" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Emp_ID") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Name_Prefix") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("First_Name")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Middle_Initial") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("Last_Name") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Gender") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("E_Mail") + "}]");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_FIELDS_NUM" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("CHECK_DATE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("SPLITRECORD" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							log4jParamters_tFileInputDelimited_1.append("ENABLE_DECODE" + " = " + "false");
							log4jParamters_tFileInputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputDelimited_1 - " + (log4jParamters_tFileInputDelimited_1));
						}
					}
					new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited");
					talendJobLogProcess(globalMap);
				}

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				int limit_tFileInputDelimited_1 = -1;
				try {

					Object filename_tFileInputDelimited_1 = "C:/Users/Solvento/Desktop/PNB/Talend testing/sample_with_dup.csv";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0 || random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Solvento/Desktop/PNB/Talend testing/sample_with_dup.csv", "ISO-8859-15", ",",
								"\n", true, 1, 0, limit_tFileInputDelimited_1, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					}

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (fid_tFileInputDelimited_1 != null && fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							columnIndexWithD_tFileInputDelimited_1 = 0;

							row1.Emp_ID = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 1;

							row1.Name_Prefix = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 2;

							row1.First_Name = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 3;

							row1.Middle_Initial = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 4;

							row1.Last_Name = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 5;

							row1.Gender = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 6;

							row1.E_Mail = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - " + e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record "
								+ fid_tFileInputDelimited_1.getRowNumber() + ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tMatchGroup_1_GroupOut main ] start
							 */

							currentVirtualComponent = "tMatchGroup_1";

							currentComponent = "tMatchGroup_1_GroupOut";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row1", "tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited",
									"tMatchGroup_1_GroupOut", "tMatchGroup_1_GroupOut", "tMatchGroupOut"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
							}

							tMatchGroup_1Struct row1_HashRow = new tMatchGroup_1Struct();

							row1_HashRow.Emp_ID = row1.Emp_ID;

							row1_HashRow.Name_Prefix = row1.Name_Prefix;

							row1_HashRow.First_Name = row1.First_Name;

							row1_HashRow.Middle_Initial = row1.Middle_Initial;

							row1_HashRow.Last_Name = row1.Last_Name;

							row1_HashRow.Gender = row1.Gender;

							row1_HashRow.E_Mail = row1.E_Mail;
							tHash_Lookup_row1.put(row1_HashRow);

							tos_count_tMatchGroup_1_GroupOut++;

							/**
							 * [tMatchGroup_1_GroupOut main ] stop
							 */

							/**
							 * [tMatchGroup_1_GroupOut process_data_begin ] start
							 */

							currentVirtualComponent = "tMatchGroup_1";

							currentComponent = "tMatchGroup_1_GroupOut";

							/**
							 * [tMatchGroup_1_GroupOut process_data_begin ] stop
							 */

							/**
							 * [tMatchGroup_1_GroupOut process_data_end ] start
							 */

							currentVirtualComponent = "tMatchGroup_1";

							currentComponent = "tMatchGroup_1_GroupOut";

							/**
							 * [tMatchGroup_1_GroupOut process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("C:/Users/Solvento/Desktop/PNB/Talend testing/sample_with_dup.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", fid_tFileInputDelimited_1.getRowNumber());

						log.info("tFileInputDelimited_1 - Retrieved records count: "
								+ fid_tFileInputDelimited_1.getRowNumber() + ".");

					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupOut end ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupOut";

				tHash_Lookup_row1.endPut();
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tFileInputDelimited_1", "tFileInputDelimited_1", "tFileInputDelimited",
						"tMatchGroup_1_GroupOut", "tMatchGroup_1_GroupOut", "tMatchGroupOut", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMatchGroup_1_GroupOut - " + ("Done."));

				ok_Hash.put("tMatchGroup_1_GroupOut", true);
				end_Hash.put("tMatchGroup_1_GroupOut", System.currentTimeMillis());

				/**
				 * [tMatchGroup_1_GroupOut end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_1", false);
				start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tFileOutputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileOutputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileOutputDelimited_1 = new StringBuilder();
							log4jParamters_tFileOutputDelimited_1.append("Parameters:");
							log4jParamters_tFileOutputDelimited_1.append("USESTREAM" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FILENAME" + " = "
									+ "\"C:/Users/Solvento/Desktop/PNB/Talend testing/outsamo.csv\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FIELDSEPARATOR" + " = " + "\",\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("APPEND" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("INCLUDEHEADER" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("COMPRESS" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("CSV_OPTION" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("CREATE" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("SPLIT" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FLUSHONROW" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ROW_MODE" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("DELETE_EMPTYFILE" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FILE_EXIST_EXCEPTION" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileOutputDelimited_1 - " + (log4jParamters_tFileOutputDelimited_1));
						}
					}
					new BytesLimit65535_tFileOutputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileOutputDelimited_1", "tFileOutputDelimited_1", "tFileOutputDelimited");
					talendJobLogProcess(globalMap);
				}

				String fileName_tFileOutputDelimited_1 = "";
				fileName_tFileOutputDelimited_1 = (new java.io.File(
						"C:/Users/Solvento/Desktop/PNB/Talend testing/outsamo.csv")).getAbsolutePath().replace("\\",
								"/");
				String fullName_tFileOutputDelimited_1 = null;
				String extension_tFileOutputDelimited_1 = null;
				String directory_tFileOutputDelimited_1 = null;
				if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
							fileName_tFileOutputDelimited_1.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					}
					directory_tFileOutputDelimited_1 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_1 = true;
				java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
				if (filetFileOutputDelimited_1.exists()) {
					throw new RuntimeException("The particular file \"" + filetFileOutputDelimited_1.getAbsoluteFile()
							+ "\" already exist. If you want to overwrite the file, please uncheck the"
							+ " \"Throw an error if the file already exist\" option in Advanced settings.");
				}
				int nb_line_tFileOutputDelimited_1 = 0;
				int splitedFileNo_tFileOutputDelimited_1 = 0;
				int currentRow_tFileOutputDelimited_1 = 0;

				final String OUT_DELIM_tFileOutputDelimited_1 = /** Start field tFileOutputDelimited_1:FIELDSEPARATOR */
						","/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
																		 * Start field
																		 * tFileOutputDelimited_1:ROWSEPARATOR
																		 */
						"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
					if (!dir_tFileOutputDelimited_1.exists()) {
						log.info("tFileOutputDelimited_1 - Creating directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath() + "'.");
						dir_tFileOutputDelimited_1.mkdirs();
						log.info("tFileOutputDelimited_1 - The directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath() + "' has been created successfully.");
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_1 = null;

				java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				if (fileToDelete_tFileOutputDelimited_1.exists()) {
					fileToDelete_tFileOutputDelimited_1.delete();
				}
				outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "ISO-8859-15"));
				if (filetFileOutputDelimited_1.length() == 0) {
					outtFileOutputDelimited_1.write("Emp_ID");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("Name_Prefix");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("First_Name");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("Middle_Initial");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("Last_Name");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("Gender");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("E_Mail");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("GID");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("GRP_SIZE");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("MASTER");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("SCORE");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("GRP_QUALITY");
					outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.flush();
				}

				resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
				resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

				/**
				 * [tFileOutputDelimited_1 begin ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupIn begin ] start
				 */

				ok_Hash.put("tMatchGroup_1_GroupIn", false);
				start_Hash.put("tMatchGroup_1_GroupIn", System.currentTimeMillis());

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupIn";

				int tos_count_tMatchGroup_1_GroupIn = 0;

				if (log.isDebugEnabled())
					log.debug("tMatchGroup_1_GroupIn - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMatchGroup_1_GroupIn {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMatchGroup_1_GroupIn = new StringBuilder();
							log4jParamters_tMatchGroup_1_GroupIn.append("Parameters:");
							log4jParamters_tMatchGroup_1_GroupIn
									.append("MATCHING_ALGORITHM" + " = " + "Simple VSR Matcher");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn.append("STORE_ON_DISK" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn.append("SEPARATE_OUTPUT" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn.append("LINK_WITH_PREVIOUS" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn.append("SORT_DATA" + " = " + "true");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn.append("OUTPUTDD" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							log4jParamters_tMatchGroup_1_GroupIn
									.append("DEACTIVATE_MATCHING_COMPUTATION" + " = " + "false");
							log4jParamters_tMatchGroup_1_GroupIn.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMatchGroup_1_GroupIn - " + (log4jParamters_tMatchGroup_1_GroupIn));
						}
					}
					new BytesLimit65535_tMatchGroup_1_GroupIn().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMatchGroup_1_GroupIn", "tMatchGroup_1_GroupIn", "tMatchGroupIn");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tMatchGroup_1_GroupIn begin ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupIn main ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupIn";

				java.util.List<java.util.List<java.util.Map<String, String>>> matchingRulesAll_tMatchGroup_1 = new java.util.ArrayList<java.util.List<java.util.Map<String, String>>>();
				java.util.List<java.util.Map<String, String>> matcherList_tMatchGroup_1 = null;
				java.util.Map<String, String> tmpMap_tMatchGroup_1 = null;
				java.util.Map<String, String> paramMapTmp_tMatchGroup_1 = null;
				java.util.List<java.util.Map<String, String>> defaultSurvivorshipRules_tMatchGroup_1 = new java.util.ArrayList<java.util.Map<String, String>>();
				java.util.List<java.util.Map<String, String>> particularSurvivorshipRules_tMatchGroup_1 = new java.util.ArrayList<java.util.Map<String, String>>();
				matcherList_tMatchGroup_1 = new java.util.ArrayList<java.util.Map<String, String>>();
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "Jaro-Winkler");
				tmpMap_tMatchGroup_1.put("RECORD_MATCH_THRESHOLD", 0.85 + "");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "First_Name");
				tmpMap_tMatchGroup_1.put("HANDLE_NULL", "nullMatchNull");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(1 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 1
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "2");
				tmpMap_tMatchGroup_1.put("MATCHING_ALGORITHM", "Simple VSR Matcher");
				tmpMap_tMatchGroup_1.put("TOKENIZATION_TYPE", "No");
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "Jaro-Winkler");
				tmpMap_tMatchGroup_1.put("RECORD_MATCH_THRESHOLD", 0.85 + "");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "Last_Name");
				tmpMap_tMatchGroup_1.put("HANDLE_NULL", "nullMatchNull");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(1 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 1
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "4");
				tmpMap_tMatchGroup_1.put("MATCHING_ALGORITHM", "Simple VSR Matcher");
				tmpMap_tMatchGroup_1.put("TOKENIZATION_TYPE", "No");
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN_IDX", "0");
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN", "Emp_ID");
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "dummy");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "Emp_ID");
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "0");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(0 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 0
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN_IDX", "1");
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN", "Name_Prefix");
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "dummy");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "Name_Prefix");
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "1");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(0 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 0
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN_IDX", "3");
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN", "Middle_Initial");
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "dummy");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "Middle_Initial");
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "3");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(0 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 0
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN_IDX", "5");
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN", "Gender");
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "dummy");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "Gender");
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "5");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(0 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 0
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				tmpMap_tMatchGroup_1 = new java.util.HashMap<String, String>();
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN_IDX", "6");
				tmpMap_tMatchGroup_1.put("REFERENCE_COLUMN", "E_Mail");
				tmpMap_tMatchGroup_1.put("MATCHING_TYPE", "dummy");
				tmpMap_tMatchGroup_1.put("ATTRIBUTE_NAME", "E_Mail");
				tmpMap_tMatchGroup_1.put("COLUMN_IDX", "6");
				try {
					tmpMap_tMatchGroup_1.put("CONFIDENCE_WEIGHT", Integer.parseInt(0 + "") + "");
				} catch (NumberFormatException e) {
					throw new NumberFormatException("The value " + 0
							+ " of Confidence weight (tMatchGroup) is not valid. It must be a positive integer and greater than 0.");
				}
				matcherList_tMatchGroup_1.add(tmpMap_tMatchGroup_1);
				java.util.Collections.sort(matcherList_tMatchGroup_1, new Comparator<java.util.Map<String, String>>() {

					@Override
					public int compare(java.util.Map<String, String> map1, java.util.Map<String, String> map2) {

						return Integer.valueOf(map1.get("COLUMN_IDX"))
								.compareTo(Integer.valueOf(map2.get("COLUMN_IDX")));
					}

				});
				matchingRulesAll_tMatchGroup_1.add(matcherList_tMatchGroup_1);
				java.util.Map<String, String> realSurShipMap_tMatchGroup_1 = null;
				realSurShipMap_tMatchGroup_1 = null;
				if (matchingRulesAll_tMatchGroup_1.size() == 0) {
					// If no matching rules in external data, try to get to rule from JOIN_KEY table
					// (which will be compatible to old version less than 5.3)
				}

				row2Struct masterRow_tMatchGroup_1 = null; // a master-row in a group
				row2Struct subRow_tMatchGroup_1 = null; // a sub-row in a group.
// key row
				tMatchGroup_1Struct hashKey_row1 = new tMatchGroup_1Struct();
// rows respond to key row 
				tMatchGroup_1Struct hashValue_row1 = new tMatchGroup_1Struct();
				java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap_tMatchGroup_1 = (java.util.concurrent.ConcurrentHashMap) globalMap
						.get("concurrentHashMap");
				concurrentHashMap_tMatchGroup_1.putIfAbsent("gid_tMatchGroup_1",
						new java.util.concurrent.atomic.AtomicLong(0L));
				java.util.concurrent.atomic.AtomicLong gid_tMatchGroup_1 = (java.util.concurrent.atomic.AtomicLong) concurrentHashMap_tMatchGroup_1
						.get("gid_tMatchGroup_1");
// master rows in a group
				final java.util.List<row2Struct> masterRows_tMatchGroup_1 = new java.util.ArrayList<row2Struct>();
// all rows in a group 
				final java.util.List<row2Struct> groupRows_tMatchGroup_1 = new java.util.ArrayList<row2Struct>();
// this Map key is MASTER GID,value is this MASTER index of all MASTERS.it will be used to get DUPLICATE GRP_QUALITY from MASTER and only in case of separate output.
				final java.util.Map<String, Double> gID2GQMap_tMatchGroup_1 = new java.util.HashMap<String, Double>();
				final double CONFIDENCE_THRESHOLD_tMatchGroup_1 = Double.valueOf(0.9);

//Long gid_tMatchGroup_1 = 0L;
				boolean forceLoop_tMatchGroup_1 = (blockRows_row1.isEmpty());
				java.util.Iterator<tMatchGroup_1_2Struct> iter_tMatchGroup_1 = blockRows_row1.keySet().iterator();

//TDQ-9172 reuse JAVA API at here.

				org.talend.dataquality.record.linkage.grouping.AbstractRecordGrouping<Object> recordGroupImp_tMatchGroup_1;
				recordGroupImp_tMatchGroup_1 = new org.talend.dataquality.record.linkage.grouping.AbstractRecordGrouping<Object>() {
					@Override
					protected void outputRow(Object[] row) {
						row2Struct outStuct_tMatchGroup_1 = new row2Struct();
						boolean isMaster = false;

						if (0 < row.length) {
							outStuct_tMatchGroup_1.Emp_ID = row[0] == null ? null : (String) row[0];
						}

						if (1 < row.length) {
							outStuct_tMatchGroup_1.Name_Prefix = row[1] == null ? null : (String) row[1];
						}

						if (2 < row.length) {
							outStuct_tMatchGroup_1.First_Name = row[2] == null ? null : (String) row[2];
						}

						if (3 < row.length) {
							outStuct_tMatchGroup_1.Middle_Initial = row[3] == null ? null : (String) row[3];
						}

						if (4 < row.length) {
							outStuct_tMatchGroup_1.Last_Name = row[4] == null ? null : (String) row[4];
						}

						if (5 < row.length) {
							outStuct_tMatchGroup_1.Gender = row[5] == null ? null : (String) row[5];
						}

						if (6 < row.length) {
							outStuct_tMatchGroup_1.E_Mail = row[6] == null ? null : (String) row[6];
						}

						if (7 < row.length) {
							outStuct_tMatchGroup_1.GID = row[7] == null ? null : (String) row[7];
						}

						if (8 < row.length) {
							outStuct_tMatchGroup_1.GRP_SIZE = row[8] == null ? null : (Integer) row[8];
						}

						if (9 < row.length) {
							outStuct_tMatchGroup_1.MASTER = row[9] == null ? null : (Boolean) row[9];
						}

						if (10 < row.length) {
							outStuct_tMatchGroup_1.SCORE = row[10] == null ? null : (Double) row[10];
						}

						if (11 < row.length) {
							outStuct_tMatchGroup_1.GRP_QUALITY = row[11] == null ? null : (Double) row[11];
						}

						if (outStuct_tMatchGroup_1.MASTER == true) {
							masterRows_tMatchGroup_1.add(outStuct_tMatchGroup_1);
						} else {
							groupRows_tMatchGroup_1.add(outStuct_tMatchGroup_1);
						}
					}

					@Override
					protected boolean isMaster(Object col) {
						return String.valueOf(col).equals("true");
					}

					@Override
					protected Object incrementGroupSize(Object oldGroupSize) {
						return Integer.parseInt(String.valueOf(oldGroupSize)) + 1;
					}

					@Override
					protected Object[] createTYPEArray(int size) {
						return new Object[size];
					}

					@Override
					protected Object castAsType(Object objectValue) {
						return objectValue;
					}

					@Override
					protected void outputRow(org.talend.dataquality.record.linkage.grouping.swoosh.RichRecord row) {
						// No implementation temporarily.

					}
				};
				recordGroupImp_tMatchGroup_1.setOrginalInputColumnSize(7);

				recordGroupImp_tMatchGroup_1.setColumnDelimiter(";");
				recordGroupImp_tMatchGroup_1.setIsOutputDistDetails(false);

				recordGroupImp_tMatchGroup_1.setAcceptableThreshold(Float.valueOf(0.85 + ""));
				recordGroupImp_tMatchGroup_1.setIsLinkToPrevious(false && true);
				recordGroupImp_tMatchGroup_1.setIsDisplayAttLabels(false);
				recordGroupImp_tMatchGroup_1.setIsGIDStringType("true".equals("true") ? true : false);
				recordGroupImp_tMatchGroup_1.setIsPassOriginalValue(false && false);
				recordGroupImp_tMatchGroup_1.setIsStoreOndisk(false);
				gID2GQMap_tMatchGroup_1.clear();

				while (iter_tMatchGroup_1.hasNext() || forceLoop_tMatchGroup_1) { // C_01

					if (forceLoop_tMatchGroup_1) {
						forceLoop_tMatchGroup_1 = false;
					} else {
						// block existing

					}
					tHash_Lookup_row1.lookup(hashKey_row1);
					masterRows_tMatchGroup_1.clear();
					groupRows_tMatchGroup_1.clear();

					// add mutch rules
					for (java.util.List<java.util.Map<String, String>> matcherList : matchingRulesAll_tMatchGroup_1) {
						recordGroupImp_tMatchGroup_1.addMatchRule(matcherList);
					}
					recordGroupImp_tMatchGroup_1.initialize();

					while (tHash_Lookup_row1.hasNext()) { // loop on each data in one block
						hashValue_row1 = tHash_Lookup_row1.next();
						// set the a loop data into inputTexts one column by one column.
						java.util.List<Object> inputTexts = new java.util.ArrayList<Object>();
						inputTexts.add(hashValue_row1.Emp_ID);

						inputTexts.add(hashValue_row1.Name_Prefix);

						inputTexts.add(hashValue_row1.First_Name);

						inputTexts.add(hashValue_row1.Middle_Initial);

						inputTexts.add(hashValue_row1.Last_Name);

						inputTexts.add(hashValue_row1.Gender);

						inputTexts.add(hashValue_row1.E_Mail);

						recordGroupImp_tMatchGroup_1
								.doGroup((Object[]) inputTexts.toArray(new Object[inputTexts.size()]));

					} // while

					recordGroupImp_tMatchGroup_1.end();
					groupRows_tMatchGroup_1.addAll(masterRows_tMatchGroup_1);

					java.util.Collections.sort(groupRows_tMatchGroup_1, new Comparator<row2Struct>() {
						public int compare(row2Struct row1, row2Struct row2) {
							if (!(row1.GID).equals(row2.GID)) {
								return (row1.GID).compareTo(row2.GID);
							} else {
								// false < true
								return (row2.MASTER).compareTo(row1.MASTER);
							}
						}
					});

					// output data
					for (row2Struct out_tMatchGroup_1 : groupRows_tMatchGroup_1) { // C_02

						if (out_tMatchGroup_1 != null) { // C_03

							// all output
							row2 = new row2Struct();
							row2.Emp_ID = out_tMatchGroup_1.Emp_ID;
							row2.Name_Prefix = out_tMatchGroup_1.Name_Prefix;
							row2.First_Name = out_tMatchGroup_1.First_Name;
							row2.Middle_Initial = out_tMatchGroup_1.Middle_Initial;
							row2.Last_Name = out_tMatchGroup_1.Last_Name;
							row2.Gender = out_tMatchGroup_1.Gender;
							row2.E_Mail = out_tMatchGroup_1.E_Mail;
							row2.GID = out_tMatchGroup_1.GID;
							row2.GRP_SIZE = out_tMatchGroup_1.GRP_SIZE;
							row2.MASTER = out_tMatchGroup_1.MASTER;
							row2.SCORE = out_tMatchGroup_1.SCORE;
							row2.GRP_QUALITY = out_tMatchGroup_1.GRP_QUALITY;

							tos_count_tMatchGroup_1_GroupIn++;

							/**
							 * [tMatchGroup_1_GroupIn main ] stop
							 */

							/**
							 * [tMatchGroup_1_GroupIn process_data_begin ] start
							 */

							currentVirtualComponent = "tMatchGroup_1";

							currentComponent = "tMatchGroup_1_GroupIn";

							/**
							 * [tMatchGroup_1_GroupIn process_data_begin ] stop
							 */
// Start of branch "row2"
							if (row2 != null) {

								/**
								 * [tFileOutputDelimited_1 main ] start
								 */

								currentComponent = "tFileOutputDelimited_1";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row2", "tMatchGroup_1_GroupIn", "tMatchGroup_1_GroupIn", "tMatchGroupIn",
										"tFileOutputDelimited_1", "tFileOutputDelimited_1", "tFileOutputDelimited"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
								}

								StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
								if (row2.Emp_ID != null) {
									sb_tFileOutputDelimited_1.append(row2.Emp_ID);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.Name_Prefix != null) {
									sb_tFileOutputDelimited_1.append(row2.Name_Prefix);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.First_Name != null) {
									sb_tFileOutputDelimited_1.append(row2.First_Name);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.Middle_Initial != null) {
									sb_tFileOutputDelimited_1.append(row2.Middle_Initial);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.Last_Name != null) {
									sb_tFileOutputDelimited_1.append(row2.Last_Name);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.Gender != null) {
									sb_tFileOutputDelimited_1.append(row2.Gender);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.E_Mail != null) {
									sb_tFileOutputDelimited_1.append(row2.E_Mail);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.GID != null) {
									sb_tFileOutputDelimited_1.append(row2.GID);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.GRP_SIZE != null) {
									sb_tFileOutputDelimited_1.append(row2.GRP_SIZE);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.MASTER != null) {
									sb_tFileOutputDelimited_1.append(row2.MASTER);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.SCORE != null) {
									sb_tFileOutputDelimited_1.append(row2.SCORE);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
								if (row2.GRP_QUALITY != null) {
									sb_tFileOutputDelimited_1.append(row2.GRP_QUALITY);
								}
								sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

								nb_line_tFileOutputDelimited_1++;
								resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

								outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());
								log.debug("tFileOutputDelimited_1 - Writing the record "
										+ nb_line_tFileOutputDelimited_1 + ".");

								tos_count_tFileOutputDelimited_1++;

								/**
								 * [tFileOutputDelimited_1 main ] stop
								 */

								/**
								 * [tFileOutputDelimited_1 process_data_begin ] start
								 */

								currentComponent = "tFileOutputDelimited_1";

								/**
								 * [tFileOutputDelimited_1 process_data_begin ] stop
								 */

								/**
								 * [tFileOutputDelimited_1 process_data_end ] start
								 */

								currentComponent = "tFileOutputDelimited_1";

								/**
								 * [tFileOutputDelimited_1 process_data_end ] stop
								 */

							} // End of branch "row2"

						} // C_03

					} // C_02

				} // C_01

				/**
				 * [tMatchGroup_1_GroupIn process_data_end ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupIn";

				/**
				 * [tMatchGroup_1_GroupIn process_data_end ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupIn end ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupIn";

				blockRows_row1.clear();
				blockRows_row1 = null;
				masterRows_tMatchGroup_1.clear();
				groupRows_tMatchGroup_1.clear();

				if (tHash_Lookup_row1 != null) {
					tHash_Lookup_row1.endGet();
				}
				globalMap.remove("tHash_Lookup_row1");

				if (log.isDebugEnabled())
					log.debug("tMatchGroup_1_GroupIn - " + ("Done."));

				ok_Hash.put("tMatchGroup_1_GroupIn", true);
				end_Hash.put("tMatchGroup_1_GroupIn", System.currentTimeMillis());

				/**
				 * [tMatchGroup_1_GroupIn end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (outtFileOutputDelimited_1 != null) {
					outtFileOutputDelimited_1.flush();
					outtFileOutputDelimited_1.close();
				}

				globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);

				resourceMap.put("finish_tFileOutputDelimited_1", true);

				log.debug("tFileOutputDelimited_1 - Written records count: " + nb_line_tFileOutputDelimited_1 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tMatchGroup_1_GroupIn", "tMatchGroup_1_GroupIn", "tMatchGroupIn", "tFileOutputDelimited_1",
						"tFileOutputDelimited_1", "tFileOutputDelimited", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileOutputDelimited_1", true);
				end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupOut finally ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupOut";

				/**
				 * [tMatchGroup_1_GroupOut finally ] stop
				 */

				/**
				 * [tMatchGroup_1_GroupIn finally ] start
				 */

				currentVirtualComponent = "tMatchGroup_1";

				currentComponent = "tMatchGroup_1_GroupIn";

				/**
				 * [tMatchGroup_1_GroupIn finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {

					java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_1");
					if (outtFileOutputDelimited_1 != null) {
						outtFileOutputDelimited_1.flush();
						outtFileOutputDelimited_1.close();
					}

				}

				/**
				 * [tFileOutputDelimited_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				ok_Hash.put("talendJobLog", false);
				start_Hash.put("talendJobLog", System.currentTimeMillis());

				currentComponent = "talendJobLog";

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				currentComponent = "talendJobLog";

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				currentComponent = "talendJobLog";

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final sample_fuz sample_fuzClass = new sample_fuz();

		int exitCode = sample_fuzClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'sample_fuz' - Done.");
		}

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}
		log.info("TalendJob: 'sample_fuz' - Start.");

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = sample_fuz.class.getClassLoader()
					.getResourceAsStream("sample_maven/sample_fuz_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = sample_fuz.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : sample_fuz");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 120655 characters generated by Talend Data Fabric on the September 30, 2021
 * 11:44:10 AM SGT
 ************************************************************************************************/