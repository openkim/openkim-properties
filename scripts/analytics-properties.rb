#!/usr/bin/env ruby
require 'yaml'
require 'pathname'

def blank?( s )
  s.nil? or s.empty?
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

def property_information( property, filepath, property_store )
  namespace = property["kim-namespace"]
  property_store[namespace] ||= {}

  hsh = property.map do |property_key, primitive|
    if property_key == "kim-namespace"
      nil
    else
      { property_key => namespace }
    end
  end.compact
  hsh = hsh.reduce({}) {|h,z| h.merge(z)}

  hsh.each do |k,v|
    if( blank?(v) and (k != "table-info"))
      k = k + "-whoops"
      v = "whoops"
      added_filepath = filepath
    end

    property_store[namespace][k] ||= {"kim-namespace" => v, "count" => 0}

    if property_store[namespace][k]["kim-namespace"] != v
      raise "doesn't match: #{v}, #{property_store[namespace][k]['kim-namespace']}, filepath"
    end

    property_store[namespace][k]["count"] += 1
    if added_filepath
      property_store[namespace][k]["files_to_check"] ||= []
      property_store[namespace][k]["files_to_check"] << added_filepath
    end
  end
end

###

property_store = {}

Dir[test_results_directory + "*/results.yaml"].each_with_index do |filepath,i|
  yaml_file_content = IO.read( filepath )
  documents = YAML.load_documents( yaml_file_content )
  documents.each do |property|
    raise "No kim-namespace for property in #{filepath}" unless property["kim-namespace"]
    property_information( property, filepath, property_store )
  end

  if( (scan_limit != 0) and (i == scan_limit) )
    break
  end
end

puts property_store.to_yaml
