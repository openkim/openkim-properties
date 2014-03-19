#!/usr/bin/env ruby

require 'yaml'
require 'pathname'

def blank?( s )
  s.nil? or (s.respond_to?(:empty) and s.empty?)
end

if blank?( ARGV[0] )
  puts "argument needed: parent directory of multiple test results"
  exit
end
test_results_directory = Pathname.new( ARGV[0] )

scan_limit = 0
if ARGV[1]
  scan_limit = (ARGV[1].to_i - 1)
end

###

def primitive_information( primitive, filepath, primitive_store )
  primitive_kim_namespace = primitive["kim-namespace"]
  primitive_store[primitive_kim_namespace] ||= {}

  hsh = primitive.map do |primitive_key, x|
    if( x.class == Array )
      x = x[0]
    end

    if(primitive_key == "kim-namespace")
      nil
    else
      { primitive_key => x }
    end
  end.compact
  hsh = hsh.reduce({}) {|h,z| h.merge(z)}

  hsh.each do |k,v|
    if( blank?(v) and (k != "table-info"))
      k = k + "-whoops"
      v = "whoops"
      added_filepath = filepath
    end

    primitive_store[primitive_kim_namespace][k] ||= {"kim-namespace" => v, "count" => 0}

    primitive_store[primitive_kim_namespace][k]["count"] += 1
    if added_filepath
      primitive_store[primitive_kim_namespace][k]["files_to_check"] ||= []
      primitive_store[primitive_kim_namespace][k]["files_to_check"] << added_filepath
    end
  end
end

def property_information( property, filepath, property_store, primitive_store )
  property_kim_namespace = property["kim-namespace"]
  property_store[property_kim_namespace] ||= {}

  hsh = property.map do |property_key, primitive|
    if property_key == "kim-namespace"
      nil
    else
      primitive_store[ primitive["kim-namespace"] ] ||= {"count" => 0, "structure" => nil}
      primitive_store[ primitive["kim-namespace"] ]["count"] += 1
      primitive_store[ primitive["kim-namespace"] ]["sample"] = primitive.tap {|a| a.delete('kim-namespace')}
      { property_key => primitive["kim-namespace"] }
    end
  end.compact
  hsh = hsh.reduce({}) {|h,z| h.merge(z)}

  hsh.each do |k,v|
    if( blank?(v) and (k != "table-info"))
      k = k + "-whoops"
      v = "whoops"
      added_filepath = filepath
    end

    property_store[property_kim_namespace][k] ||= {"kim-namespace" => v, "count" => 0}

    property_store[property_kim_namespace][k]["count"] += 1
    if added_filepath
      property_store[property_kim_namespace][k]["files_to_check"] ||= []
      property_store[property_kim_namespace][k]["files_to_check"] << added_filepath
    end
  end
end

###

property_store = {}
primitive_store = {}

Dir[test_results_directory + "*/results.yaml"].each_with_index do |filepath,i|
  yaml_file_content = IO.read( filepath )
  documents = YAML.load_documents( yaml_file_content )
  documents.each do |property|
    raise "No kim-namespace for property in #{filepath}" unless property["kim-namespace"]
    property_information( property, filepath, property_store, primitive_store )
  end

  if( (scan_limit != 0) and (i == scan_limit) )
    break
  end
end

puts primitive_store.to_yaml
