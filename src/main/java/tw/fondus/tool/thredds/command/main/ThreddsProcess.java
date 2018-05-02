package tw.fondus.tool.thredds.command.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.esri.EsriGrid;
import tw.fondus.commons.fews.pi.esri.EsriGridHeader;
import tw.fondus.commons.nc.NetCDFReader;
import tw.fondus.commons.util.coordinate.CoordinatePoint;
import tw.fondus.commons.util.coordinate.CoordinateUtils;
import tw.fondus.tool.thredds.command.process.ProgramArguments;
import tw.fondus.tool.thredds.xml.Url;
import tw.fondus.tool.thredds.xml.Wexflow_THREDDS;
import ucar.ma2.Array;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

public class ThreddsProcess {
	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	public static void main( String[] args ) {
		ProgramArguments arguments = new ProgramArguments();
		new ThreddsProcess().run( args, arguments );
	}

	public void run( String[] args, ProgramArguments arguments ) {
		JCommander command = new JCommander( arguments );

		command.parse( args );

		if ( arguments.isHelp() ) {
			command.usage();
		} else {
			Path configPath = Paths.get( Strman.append( arguments.getBasePath(), arguments.getConfig() ) );
			Preconditions.checkState( Files.exists( configPath ),
					"Can not find report mapping of XML configuration exist." );

			this.transformProcess( configPath );
		}
	}

	public void transformProcess( Path configPath ) {
		try {
			Wexflow_THREDDS wexflow_THREDDS = XMLUtils.fromXML( configPath.toFile(), Wexflow_THREDDS.class );
			List<Url> urls = wexflow_THREDDS.getUrls().getList();
			urls.forEach( url -> {
				try (NetCDFReader reader = new NetCDFReader() ) {
					reader.open( url.getInput() );

					List<Variable> variables = reader.getVariables();
					EsriGridHeader header = new EsriGridHeader();
					EsriGrid grid = new EsriGrid();
					Array datas = null;

					for ( int i = 0; i < variables.size(); i++ ) {
						if ( variables.get( i ).getFullName().equals( wexflow_THREDDS.getVariableNames().getX() ) ) {

							header.setNCols( variables.get( i ).getDimension( 0 ).getLength() );
							header.setXll( reader.readVariableValue( variables.get( i ).getFullName() ).getFloat( 0 ) );

						} else if ( variables.get( i )
								.getFullName()
								.equals( wexflow_THREDDS.getVariableNames().getY() ) ) {

							header.setNRows( variables.get( i ).getDimension( 0 ).getLength() );
							header.setYll( reader.readVariableValue( variables.get( i ).getFullName() ).getFloat( 0 ) );

						} else if ( variables.get( i )
								.getFullName()
								.equals( wexflow_THREDDS.getVariableNames().getValue() ) ) {

							datas = reader.readVariableValue( variables.get( i ).getFullName() );
							List<Attribute> attributes = variables.get( i ).getAttributes();
							attributes.stream()
									.filter( attribute -> attribute.getFullName()
											.equals( wexflow_THREDDS.getVariableNames().getMissingValue() ) )
									.forEach( attribute -> {
										header.setMissingValue( (float) attribute.getValue( 0 ) );
									} );
							;
						}

					}

					CoordinatePoint coordinatePoint = CoordinateUtils.transformWGS84ToTWD97( header.getXll(),
							header.getYll() );
					header.setXll( coordinatePoint.getX().floatValue() );
					header.setYll( coordinatePoint.getY().floatValue() );
					header.setCellsize( 40 );
					grid.setHeader( header );

					for(int i = 0 ; i < datas.getSize() ; i++){
						grid.addData( datas.getFloat( i ) );
					}
					
					grid.writeASCII( url.getOutput() );
				} catch (Exception e) {
					logger.error( "transformProcess: transform NetCDF to asc has something wrong!" );
				}
			} );
		} catch (Exception e) {
			logger.error( "transformProcess: load xml configuration has something wrong!" );
		}
	}
}
